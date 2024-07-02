package com.example.demo.servicios;

import com.example.demo.Entidades.Cliente;
import com.example.demo.Entidades.Persona;
import com.example.demo.Entidades.Usuario;
import com.example.demo.Entidades.Rol;
import com.example.demo.repositorios.PersonaRepository;
import com.example.demo.repositorios.RolRepository;
import com.example.demo.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    RolRepository rolRepository;
    @Autowired
    PersonaRepository personaRepository;

    @Autowired
    private ClienteService clienteService;

    //@Autowired
    private PasswordEncoder passwordEncoder;

    UsuarioService(RolRepository rolRepository, UsuarioRepository usuarioRepository, PersonaRepository personaRepository){
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        passwordEncoder = Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
        return passwordEncoder;
    }

    public void registrarNuevoUsuario(Usuario usuario) {
        // Crear una nueva instancia de Cliente y asignar los valores desde Persona
        Cliente cliente = new Cliente();
        cliente.setCedula(usuario.getPersona().getCedula());
        cliente.setNombre(usuario.getPersona().getNombre());
        cliente.setApellido(usuario.getPersona().getApellido());
        cliente.setFechaNacimiento(usuario.getPersona().getFechaNacimiento());
        cliente.setCorreoElectronico(usuario.getPersona().getCorreoElectronico());
        cliente.setTelefono(usuario.getPersona().getTelefono());

        // Asignar el cliente a la persona del usuario
        usuario.setPersona(cliente);

        // Manejar los roles del usuario
        List<Rol> roles = new ArrayList<>();
        boolean esCliente = false;
        if (usuario.getRoles() != null && !usuario.getRoles().isEmpty()) {
            for (Rol rol : usuario.getRoles()) {
                Rol encontrado = rolRepository.findByDescripcion(rol.getDescripcion());
                roles.add(encontrado);
                if ("ROLE_CLIENTE".equals(encontrado.getDescripcion())) {
                    esCliente = true;
                }
            }
        }
        usuario.setRoles(roles);

        String encodedPassword = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(encodedPassword);

        usuarioRepository.save(usuario);

        System.out.println("Roles del usuario: " + usuario.getRoles());
    }

    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public boolean autenticarUsuario(String usernameIngresado, String passwordIngresado) {
        Usuario usuarioEnBD = usuarioRepository.findByUsername(usernameIngresado);
        if (usuarioEnBD != null) {
            // Encriptar la contraseña ingresada por el usuario antes de comparar
            String ingresadoEncoded = passwordEncoder.encode(passwordIngresado);
            return passwordEncoder.matches(passwordIngresado, usuarioEnBD.getPassword());
        }
        return false;
    }


    public void actualizarUsuario(Usuario usuarioExistente, Usuario usuarioActualizado) {
        Persona personaExistente = usuarioExistente.getPersona();
        Persona personaActualizada = usuarioActualizado.getPersona();

        personaExistente.setNombre(personaActualizada.getNombre());
        personaExistente.setApellido(personaActualizada.getApellido());
        personaExistente.setFechaNacimiento(personaActualizada.getFechaNacimiento());
        personaExistente.setCorreoElectronico(personaActualizada.getCorreoElectronico());
        personaExistente.setTelefono(personaActualizada.getTelefono());

        personaRepository.save(personaExistente);

        // Solo actualizar la contraseña si se proporciona una nueva y es diferente a la existente
        if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().isEmpty() &&
                !passwordEncoder.matches(usuarioActualizado.getPassword(), usuarioExistente.getPassword())) {
            String encodedNewPassword = passwordEncoder.encode(usuarioActualizado.getPassword());
            usuarioExistente.setPassword(encodedNewPassword);

            // Línea de depuración
            System.out.println("Nueva contraseña codificada: " + encodedNewPassword);
        }

        usuarioExistente.setRoles(usuarioActualizado.getRoles());

        usuarioRepository.save(usuarioExistente);
    }

    public Usuario findByCedula(Long cedula) {
        return usuarioRepository.findByPersona_Cedula(cedula);
    }

}
