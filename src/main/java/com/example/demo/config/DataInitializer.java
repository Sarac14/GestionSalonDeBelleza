package com.example.demo.config;

import com.example.demo.Entidades.*;
import com.example.demo.repositorios.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;
    private final ProductoProveedorRepository productoProveedorRepository;


    public DataInitializer(ClienteRepository clienteRepository, ServicioRepository servicioRepository, EmpleadoRepository empleadoRepository, CitaRepository citaRepository, RolRepository rolRepository, UsuarioRepository usuarioRepository, ProductoRepository productoRepository, ProveedorRepository proveedorRepository, ProductoProveedorRepository productoProveedorRepository) {
        this.clienteRepository = clienteRepository;
        this.servicioRepository = servicioRepository;
        this.empleadoRepository = empleadoRepository;
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
        this.productoProveedorRepository = productoProveedorRepository;

    }

    @Override
    public void run(String... args) throws Exception {
        if (clienteRepository.count() == 0) {
            clienteRepository.save(new Cliente(402626891,"Cliente 1","Cliente","2002-12-14","sara@gmail.com",123));
            clienteRepository.save(new Cliente(402626892,"Cliente 2","Cliente","2002-09-14","sara@gmail.com",123));
            clienteRepository.save(new Cliente(402626893,"Cliente 3","Cliente","2002-09-14","sara@gmail.com",123));
            clienteRepository.save(new Cliente(402626894,"Cliente 4","Cliente","2002-09-14","sara@gmail.com",123));
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
