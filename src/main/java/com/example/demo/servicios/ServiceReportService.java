package com.example.demo.servicios;

import com.example.demo.Entidades.Factura;
import com.example.demo.Entidades.ServicioCita;
import com.example.demo.performance.BillingByServiceCategory;
import com.example.demo.performance.TopService;
import com.example.demo.repositorios.FacturaRepository;
import com.example.demo.repositorios.ServicioCitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ServiceReportService {
    @Autowired
    private ServicioCitaRepository servicioCitaRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    public List<BillingByServiceCategory> getBillingByServiceCategory(LocalDate startDate, LocalDate endDate) {

        // Busca todas las citas de servicio dentro del rango de fechas especificado
        List<ServicioCita> serviciosCita = servicioCitaRepository.findByFechaBetween(startDate, endDate);

        // Crea un mapa para almacenar el ingreso total por categoría de servicio
        Map<String, Double> categoryIncomeMap = new HashMap<>();

        // Variable temporal para la factura
        Factura factura;

        // Itera a través de cada cita de servicio encontrada
        for (ServicioCita servicioCita : serviciosCita) {

            // Busca la factura asociada a la cita de servicio
            factura = facturaRepository.findByIdCita(servicioCita.getCita().getId());

            // Obtiene la categoría del servicio asociado a la cita de servicio
            String category = servicioCita.getServicio().getCategoria();

            // Calcula el ingreso por servicio dividiendo el monto total de la factura por el número de servicios en la cita
            double income = factura.getTotalPagar() / servicioCita.getCita().getServiciosCita().size();

            // Acumula el ingreso en el mapa para la categoría correspondiente
            // Si la categoría no existe en el mapa, se inicializa con 0.0
            categoryIncomeMap.put(category, categoryIncomeMap.getOrDefault(category, 0.0) + income);
        }

        // Convierte el mapa de ingresos por categoría en una lista de objetos BillingByServiceCategory
        return categoryIncomeMap.entrySet().stream()
                .map(entry -> new BillingByServiceCategory(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public List<TopService> getTopServices(LocalDate startDate, LocalDate endDate) {

        // Busca todas las citas de servicio dentro del rango de fechas especificado
        List<ServicioCita> serviciosCita = servicioCitaRepository.findByFechaBetween(startDate, endDate);

        // Crea un mapa para almacenar la cantidad de citas por nombre de servicio
        Map<String, Integer> serviceCountMap = new HashMap<>();

        // Itera a través de cada cita de servicio encontrada
        for (ServicioCita servicioCita : serviciosCita) {

            // Obtiene el nombre del servicio asociado a la cita de servicio
            String serviceName = servicioCita.getServicio().getNombre();

            // Acumula la cantidad de citas para el nombre del servicio correspondiente
            // Si el nombre del servicio no existe en el mapa, se inicializa con 0
            serviceCountMap.put(serviceName, serviceCountMap.getOrDefault(serviceName, 0) + 1);
        }

        // Convierte el mapa de cantidad de citas por servicio en una lista de objetos TopService
        // y ordena la lista de forma descendente por cantidad de citas
        return serviceCountMap.entrySet().stream()
                .map(entry -> new TopService(entry.getKey(), entry.getValue()))
                .sorted((s1, s2) -> Integer.compare(s2.getAppointmentCount(), s1.getAppointmentCount()))
                .collect(Collectors.toList());
    }

}
