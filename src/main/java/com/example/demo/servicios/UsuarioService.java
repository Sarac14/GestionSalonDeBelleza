package com.example.demo.servicios;

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
        Persona persona = new Persona();
        persona.setCedula(usuario.getPersona().getCedula());
        persona.setNombre(usuario.getPersona().getNombre());
        persona.setApellido(usuario.getPersona().getApellido());
        persona.setFechaNacimiento(usuario.getPersona().getFechaNacimiento());
        persona.setCorreoElectronico(usuario.getPersona().getCorreoElectronico());
        persona.setTelefono(usuario.getPersona().getTelefono());

        persona = personaRepository.save(persona);

        usuario.setPersona(persona);

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

        if (esCliente) {
            clienteService.crearCliente(persona);
        }
        usuarioRepository.save(usuario);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
        System.out.println("Roles del usuario: " + usuario.getRoles());
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");

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

}
