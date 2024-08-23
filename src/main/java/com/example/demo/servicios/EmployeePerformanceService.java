package com.example.demo.servicios;

import com.example.demo.Entidades.Cita;
import com.example.demo.performance.EmployeePerformance;
import com.example.demo.Entidades.Factura;
import com.example.demo.Entidades.ServicioCita;
import com.example.demo.repositorios.FacturaRepository;
import com.example.demo.repositorios.ServicioCitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeePerformanceService {
    @Autowired
    private ServicioCitaRepository servicioCitaRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    /*
    Funcion para medir el rendimiento de un empleado a partir de las citas realizadas y el dinero generado
    */

    public EmployeePerformance calculatePerformance(Long empleadoId, LocalDate startDate, LocalDate endDate) {

        // Encuentra todas las citas de servicio para el empleado dentro del rango de fechas especificado
        List<ServicioCita> serviciosCita = servicioCitaRepository.findByEmpleadoIdAndFechaBetween(empleadoId, startDate, endDate);

        // Calcula el ingreso total obtenido por el empleado
        double totalIncome = serviciosCita.stream()
                .mapToDouble(sc -> {
                    // Obtiene la Cita (cita) vinculada al ServicioCita actual
                    Cita cita = sc.getCita();

                    // Verifica si la cita está deshabilitada (habilitada == false)
                    if (!cita.isVigente()) {
                        // Encuentra la Factura (factura) asociada a la Cita
                        Factura factura = facturaRepository.findByIdCita(cita.getId());

                        // Divide el monto total de la factura por el número de servicios en la Cita
                        // para obtener el ingreso por servicio para este ServicioCita
                        return factura.getTotalPagar() / cita.getServiciosCita().size();
                    }

                    // Si la cita está habilitada, retorna 0 para no afectar la suma total
                    return 0.0;
                })
                .sum();

        // Obtiene el número total de citas para el empleado durante el período
        int appointmentCount = serviciosCita.size();

        // Crea un nuevo objeto EmployeePerformance
        EmployeePerformance performance = new EmployeePerformance();

        // Establece el ID del empleado, la fecha del informe, el ingreso total y el número de citas en el objeto de desempeño
        performance.setEmployeeId(empleadoId);
        performance.setReportDate(LocalDate.now());
        performance.setTotalIncome(totalIncome);
        performance.setAppointmentCount(appointmentCount);

        // Devuelve el desempeño calculado del empleado para el período dado
        return performance;
    }

    /*
Funcion para medir el rendimiento de un grupo empleado con la misma categoria a partir de las citas realizadas y el dinero generado
*/
    public List<EmployeePerformance> calculatePerformanceByCategory(String categoria, LocalDate startDate, LocalDate endDate) {

        // Busca todas las citas de servicio para la categoría especificada dentro del rango de fechas
        List<ServicioCita> serviciosCita = servicioCitaRepository.findByEmpleadoCategoriaAndFechaBetween(categoria, startDate, endDate);

        // Crea un mapa para almacenar el desempeño de los empleados por ID
        Map<Long, EmployeePerformance> performanceMap = new HashMap<>();

        // Variable temporal para la factura
        Factura factura;

        // Itera a través de cada cita de servicio encontrada
        for (ServicioCita servicioCita : serviciosCita) {

            // Obtiene el ID del empleado asociado a la cita de servicio
            Long empleadoId = servicioCita.getEmpleado().getId();

            // Busca o crea un objeto EmployeePerformance para el empleado actual
            EmployeePerformance performance = performanceMap.getOrDefault(empleadoId, new EmployeePerformance());

            // Configura los valores básicos del objeto de desempeño (solo en la primera iteración por empleado)
            if (performance.getEmployeeId() == null) {  // Omitir si ya se establecieron antes
                performance.setEmployeeId(empleadoId);
                performance.setReportDate(LocalDate.now());
            }

            // Busca la factura asociada a la cita de servicio
            factura = facturaRepository.findByIdCita(servicioCita.getCita().getId());

            // Calcula el ingreso por servicio dividiendo el monto total de la factura por el número de servicios en la cita
            double ingreso = factura.getTotalPagar() / servicioCita.getCita().getServiciosCita().size();

            // Acumula el ingreso y el número de citas para el empleado actual
            performance.setTotalIncome(performance.getTotalIncome() + ingreso);
            performance.setAppointmentCount(performance.getAppointmentCount() + 1);

            // Actualiza el mapa con el objeto de desempeño actualizado
            performanceMap.put(empleadoId, performance);
        }

        // Convierte el mapa de desempeño en una lista y la devuelve
        return new ArrayList<>(performanceMap.values());
    }
}
