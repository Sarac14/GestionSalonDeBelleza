package com.example.demo.config;

import com.example.demo.Entidades.*;
import com.example.demo.repositorios.*;
import com.example.demo.servicios.FacturaService;
import com.example.demo.servicios.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.time.LocalTime;


@Component
public class DataInitializer implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final ServicioRepository servicioRepository;
    private static EmpleadoRepository empleadoRepository;
    private final RolRepository rolRepository;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;
    private final ProductoProveedorRepository productoProveedorRepository;

    private final CitaRepository citaRepository;

    private final ServicioCitaRepository servicioCitaRepository;
    private final FacturaRepository facturaRepository;
    private final FacturaService facturaService;


    public DataInitializer(ClienteRepository clienteRepository, ServicioRepository servicioRepository, EmpleadoRepository empleadoRepository, CitaRepository citaRepository,
                           RolRepository rolRepository, UsuarioRepository usuarioRepository, ProductoRepository productoRepository, ProveedorRepository proveedorRepository,
                           ProductoProveedorRepository productoProveedorRepository, UsuarioService usuarioService, ServicioCitaRepository servicioCitaRepository,
                           FacturaRepository facturaRepository, FacturaService facturaService) {
        this.clienteRepository = clienteRepository;
        this.servicioRepository = servicioRepository;
        this.empleadoRepository = empleadoRepository;
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
        this.productoProveedorRepository = productoProveedorRepository;
        this.usuarioService = usuarioService;
        this.citaRepository = citaRepository;
        this.servicioCitaRepository = servicioCitaRepository;
        this.facturaRepository = facturaRepository;
        this.facturaService = facturaService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (clienteRepository.count() == 0) {
            clienteRepository.save(new Cliente(402626891L,"Cliente 1","Cliente","2002-12-14","sara@gmail.com", 123L));
            clienteRepository.save(new Cliente(402626892L,"Cliente 2","Cliente","2002-09-14","sara@gmail.com", 123L));
            clienteRepository.save(new Cliente(402626893L,"Cliente 3","Cliente","2002-09-14","sara@gmail.com", 123L));
            clienteRepository.save(new Cliente(402626894L,"Cliente 4","Cliente","2002-09-14","sara@gmail.com", 123L));
        }

        if (servicioRepository.count() == 0) {
            servicioRepository.save(new Servicio("Servicio 1", 10, "Secado",35 ));
            servicioRepository.save(new Servicio("Servicio 2", 20, "Lavado", 10));
            servicioRepository.save(new Servicio("Servicio 3", 30, "Uñas", 40));
            servicioRepository.save(new Servicio("Servicio 4", 10, "Secado", 35));

        }

        if (empleadoRepository.count() == 0) {

            LocalTime horaEntrada = LocalTime.of(8, 30);
            LocalTime horaSalida = LocalTime.of(17, 30);
            LocalTime horaComida = LocalTime.of(12, 30);


            empleadoRepository.save(new Empleado("Ana García", "Secado", horaEntrada,horaSalida,horaComida,"monday"));
            empleadoRepository.save(new Empleado("Carlos Rodríguez", "Secado", horaEntrada,horaSalida,horaComida,"monday"));
            empleadoRepository.save(new Empleado("María López", "Lavado"));
            empleadoRepository.save(new Empleado("Pedro Fernández", "Lavado"));
            empleadoRepository.save(new Empleado("Laura González", "Uñas"));
        }

        if (rolRepository.count() == 0) {
            rolRepository.save(new Rol("ROLE_ADMIN"));
            rolRepository.save(new Rol("ROLE_EMPLEADO"));
            rolRepository.save(new Rol("ROLE_CLIENTE"));
        }

        if (usuarioRepository.count() == 0) {
            Persona persona = new Persona(40219065769L,"Nombre", "Apellido", "1990-01-01", "correo@ejemplo.com", 1234567890L);
            List<Rol> roles = new ArrayList<>();
            roles.add(rolRepository.findByDescripcion("ROLE_ADMIN"));
            Usuario usuario = new Usuario("username","password",persona,roles);
            usuarioService.registrarNuevoUsuario(usuario);
        }

       /* if (citaRepository.count() == 0) {
            Cliente cliente1 = clienteRepository.findFirstByCedula(402626891L).get();
            Cliente cliente2 = clienteRepository.findFirstByCedula(402626892L).get();
            Cliente cliente3 = clienteRepository.findFirstByCedula(402626893L).get();
            Cliente cliente4 = clienteRepository.findFirstByCedula(402626894L).get();

           // Crear servicios y empleados
            Servicio servicio1 = servicioRepository.findByNombre("Servicio 1");
            Servicio servicio2 = servicioRepository.findByNombre("Servicio 2");
            Servicio servicio3 = servicioRepository.findByNombre("Servicio 3");
            Empleado empleado1 = empleadoRepository.findByNombre("Ana García");
            Empleado empleado2 = empleadoRepository.findByNombre("Carlos Rodríguez");
            Empleado empleado3 = empleadoRepository.findByNombre("María López");

            // Crear lista de servicios para cada cita
            List<ServicioCita> serviciosCita1 = List.of(
                    new ServicioCita(null, servicio1, empleado1, LocalTime.of(9, 0), LocalTime.of(9, 30))
            );
            List<ServicioCita> serviciosCita2 = List.of(
                    new ServicioCita(null, servicio1, empleado2, LocalTime.of(11, 0), LocalTime.of(11, 30))
            );
            List<ServicioCita> serviciosCita3 = List.of(
                    new ServicioCita(null, servicio2, empleado3, LocalTime.of(14, 0), LocalTime.of(14, 45))
            );
            List<ServicioCita> serviciosCita4 = List.of(
                    new ServicioCita(null, servicio1, empleado1, LocalTime.of(16, 0), LocalTime.of(16, 30))
            );
            List<ServicioCita> serviciosCita5 = List.of(
                    new ServicioCita(null, servicio1, empleado2, LocalTime.of(10, 0), LocalTime.of(10, 30))
            );

            // Crear citas con la lista de servicios correspondiente
            Cita cita1 = new Cita(null, cliente1, LocalDate.parse("2024-08-20"), LocalTime.of(9, 0), serviciosCita1);
            Cita cita2 = new Cita(null, cliente2, LocalDate.parse("2024-08-20"), LocalTime.of(11, 0), serviciosCita2);
            Cita cita3 = new Cita(null, cliente3, LocalDate.parse("2024-08-20"), LocalTime.of(14, 0), serviciosCita3);
            Cita cita4 = new Cita(null, cliente4, LocalDate.parse("2024-08-21"), LocalTime.of(16, 0), serviciosCita4);
            Cita cita5 = new Cita(null, cliente1, LocalDate.parse("2024-08-21"), LocalTime.of(10, 0), serviciosCita5);

            // Guardar las citas en la base de datos
            citaRepository.save(cita1);
            citaRepository.save(cita2);
            citaRepository.save(cita3);
            citaRepository.save(cita4);
            citaRepository.save(cita5);

            // Asignar la cita a cada servicio y guardar los serviciosCita
            serviciosCita1.forEach(sc -> sc.setCita(cita1));
            serviciosCita2.forEach(sc -> sc.setCita(cita2));
            serviciosCita3.forEach(sc -> sc.setCita(cita3));
            serviciosCita4.forEach(sc -> sc.setCita(cita4));
            serviciosCita5.forEach(sc -> sc.setCita(cita5));

            servicioCitaRepository.saveAll(serviciosCita1);
            servicioCitaRepository.saveAll(serviciosCita2);
            servicioCitaRepository.saveAll(serviciosCita3);
            servicioCitaRepository.saveAll(serviciosCita4);
            servicioCitaRepository.saveAll(serviciosCita5);

            // Crear facturas para las citas
            facturaRepository.save(new Factura(cita1.getId(), 120.0f, "Tarjeta"));
            facturaRepository.save(new Factura(cita2.getId(), 80.0f, "Efectivo"));
            facturaRepository.save(new Factura(cita3.getId(), 150.0f, "Tarjeta"));
            facturaRepository.save(new Factura(cita4.getId(), 90.0f, "Efectivo"));
            facturaRepository.save(new Factura(cita5.getId(), 110.0f, "Tarjeta"));


            facturaService.crearFactura(cita1.getCliente(),empleado1,cita1,0,0.18f,"Tarjeta",null);

        }
*/

        /*List<Proveedor> proveedores = new ArrayList<>();
        if (proveedorRepository.count() == 0) {
            Proveedor proveedor1 = new Proveedor("Proveedor 1", "Contacto 1", "123456789", "contacto1@proveedor.com", "Dirección 1", new ArrayList<>());
            Proveedor proveedor2 = new Proveedor("Proveedor 2", "Contacto 2", "987654321", "contacto2@proveedor.com", "Dirección 2", new ArrayList<>());
            proveedores.add(proveedor1);
            proveedores.add(proveedor2);
            proveedorRepository.saveAll(proveedores);
        }

        List<Producto> productos = new ArrayList<>();
        if (productoRepository.count() == 0) {
            Producto producto1 = new Producto("Marca 1", "Producto 1", "Descripción del Producto 1", 5, 100.0f, 5,50, new HashSet<>());
            Producto producto2 = new Producto("Marca 2", "Producto 2", "Descripción del Producto 2", 3, 150.0f, 5, 30,new HashSet<>());
            Producto producto3 = new Producto("Marca 3", "Producto 3", "Descripción del Producto 3", 10, 200.0f, 2,20, new HashSet<>());
            productos.add(producto1);
            productos.add(producto2);
            productos.add(producto3);
            productoRepository.saveAll(productos);
        }

        if (productoProveedorRepository.count() == 0) {
            for (Proveedor proveedor : proveedores) {
                for (Producto producto : productos) {
                    ProductoProveedor productoProveedor = new ProductoProveedor();
                    ProductoProveedorId productoProveedorId = new ProductoProveedorId(producto.getId(), proveedor.getId());
                    productoProveedor.setId(productoProveedorId);
                    productoProveedor.setProducto(producto);
                    productoProveedor.setProveedor(proveedor);
                    productoProveedorRepository.save(productoProveedor);
                }
            }
        }*/
    }
}
