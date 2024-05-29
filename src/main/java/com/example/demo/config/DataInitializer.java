package com.example.demo.config;

import com.example.demo.Entidades.Cliente;
import com.example.demo.Entidades.Empleado;
import com.example.demo.Entidades.Rol;
import com.example.demo.Entidades.Servicio;
import com.example.demo.repositorios.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final ServicioRepository servicioRepository;
    private static EmpleadoRepository empleadoRepository;
    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;


    public DataInitializer(ClienteRepository clienteRepository, ServicioRepository servicioRepository, EmpleadoRepository empleadoRepository, CitaRepository citaRepository, RolRepository rolRepository, UsuarioRepository usuarioRepository) {
        this.clienteRepository = clienteRepository;
        this.servicioRepository = servicioRepository;
        this.empleadoRepository = empleadoRepository;
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
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
            servicioRepository.save(new Servicio("Servicio 1", 10, "Secado" ));
            servicioRepository.save(new Servicio("Servicio 2", 20, "Lavado"));
            servicioRepository.save(new Servicio("Servicio 3", 30, "Uñas"));
        }

        if (empleadoRepository.count() == 0) {

            empleadoRepository.save(new Empleado("Ana García", "Secado"));
            empleadoRepository.save(new Empleado("Carlos Rodríguez", "Secado"));
            empleadoRepository.save(new Empleado("María López", "Lavado"));
            empleadoRepository.save(new Empleado("Pedro Fernández", "Lavado"));
            empleadoRepository.save(new Empleado("Laura González", "Uñas"));
        }

        if (rolRepository.count() == 0) {
            rolRepository.save(new Rol("ROLE_ADMIN"));
            rolRepository.save(new Rol("ROLE_EMPLEADO"));
            rolRepository.save(new Rol("ROLE_CLIENTE"));
        }
    }
}
