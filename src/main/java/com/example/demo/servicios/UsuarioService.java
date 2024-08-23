package com.example.demo.servicios;

import com.example.demo.Entidades.Cliente;
import com.example.demo.Entidades.Persona;
import com.example.demo.Entidades.Usuario;
import com.example.demo.Entidades.Rol;
import com.example.demo.Entidades.Empleado;
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
import java.util.Optional;

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
        Cliente cliente = new Cliente();
        cliente.setCedula(usuario.getPersona().getCedula());
        cliente.setNombre(usuario.getPersona().getNombre());
        cliente.setApellido(usuario.getPersona().getApellido());
        cliente.setFechaNacimiento(usuario.getPersona().getFechaNacimiento());
        cliente.setCorreoElectronico(usuario.getPersona().getCorreoElectronico());
        cliente.setTelefono(usuario.getPersona().getTelefono());

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

    public boolean deshabilitarUsuario(String username) {
        Optional<Usuario> usuarioOpt = Optional.ofNullable(usuarioRepository.findByUsername(username));
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setHabilitado(false);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    public boolean habilitarUsuario(String username) {
        Optional<Usuario> usuarioOpt = Optional.ofNullable(usuarioRepository.findByUsername(username));
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setHabilitado(true);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    public void actualizarUsuario(Usuario usuarioExistente, Usuario usuarioActualizado) {
        Persona personaExistente = getPersona(usuarioExistente, usuarioActualizado);

        personaRepository.save(personaExistente);

        // Actualizamos los roles solo si se proporcionan
        if (usuarioActualizado.getRoles() != null && !usuarioActualizado.getRoles().isEmpty()) {
            usuarioExistente.setRoles(usuarioActualizado.getRoles());
        }
        usuarioRepository.save(usuarioExistente);
    }

    private static Persona getPersona(Usuario usuarioExistente, Usuario usuarioActualizado) {
        Persona personaExistente = usuarioExistente.getPersona();
        Persona personaActualizada = usuarioActualizado.getPersona();

        personaExistente.setNombre(personaActualizada.getNombre());
        personaExistente.setApellido(personaActualizada.getApellido());
        personaExistente.setFechaNacimiento(personaActualizada.getFechaNacimiento());
        personaExistente.setCorreoElectronico(personaActualizada.getCorreoElectronico());
        personaExistente.setTelefono(personaActualizada.getTelefono());
        return personaExistente;
    }

    public Usuario findByCedula(Long cedula) {
        return usuarioRepository.findByPersona_Cedula(cedula);
    }

    public List<Usuario> buscarUsuarios(String query) {
        Long cedula = null;
        try {
            cedula = Long.parseLong(query);
        } catch (NumberFormatException e) {
            // Si no se puede convertir a Long, dejamos cedula como null
        }
        return usuarioRepository.findByPersonaCedulaOrUsernameContaining(cedula, query);
    }

    public void changePassword(Long cedula, String currentPassword, String newPassword) {
        Usuario usuario = usuarioRepository.findByPersona_Cedula(cedula);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if (!passwordEncoder.matches(currentPassword, usuario.getPassword())) {
            throw new RuntimeException("Contraseña actual incorrecta");
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        usuario.setPassword(encodedNewPassword);
        usuarioRepository.save(usuario);
    }

    public void registrarNuevoUsuarioEmpleado(Usuario usuario) {
        Persona persona = usuario.getPersona();

        // Verificar si la persona es una instancia de Empleado
        if (!(persona instanceof Empleado)) {
            throw new IllegalArgumentException("La persona asociada al usuario no es un Empleado");
        }

        Empleado empleado = (Empleado) persona;

        // Manejar los roles del usuario
        List<Rol> roles = new ArrayList<>();
        Rol rolEmpleado = rolRepository.findByDescripcion("ROLE_EMPLEADO");
        if (rolEmpleado == null) {
            rolEmpleado = new Rol("ROLE_EMPLEADO");
            rolRepository.save(rolEmpleado);
        }
        roles.add(rolEmpleado);
        usuario.setRoles(roles);

        // Encriptar la contraseña
        String encodedPassword = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(encodedPassword);

        // Guardar el usuario
        usuarioRepository.save(usuario);
    }


    /*public void registrarNuevoUsuarioEmpleado(Usuario usuario) {
        Persona personaDeUsuario = usuario.getPersona();

        // Verificar si la persona asociada al usuario es de tipo Empleado
        if (personaDeUsuario instanceof Empleado) {
            Empleado empleado = (Empleado) personaDeUsuario;

            // Crear un nuevo usuario con el empleado existente
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setUsername(usuario.getUsername());
            nuevoUsuario.setPassword(usuario.getPassword());
            nuevoUsuario.setPersona(empleado);

            // Manejar los roles del usuario
            List<Rol> roles = new ArrayList<>();
            Rol rolEmpleado = rolRepository.findByDescripcion("ROLE_EMPLEADO");
            if (rolEmpleado == null) {
                rolEmpleado = new Rol("ROLE_EMPLEADO");
                rolRepository.save(rolEmpleado);
            }
            roles.add(rolEmpleado);
            nuevoUsuario.setRoles(roles);

            // Encriptar la contraseña
            String encodedPassword = passwordEncoder.encode(nuevoUsuario.getPassword());
            nuevoUsuario.setPassword(encodedPassword);

            // Guardar el usuario
            usuarioRepository.save(nuevoUsuario);
        } else {
            throw new IllegalArgumentException("La persona asociada al usuario no es de tipo Empleado.");
        }
    }*/

    /*public void registrarNuevoUsuarioEmpleado(Usuario usuario) {
        System.out.println("Tipo de Persona recibido: " + usuario.getPersona().getClass().getName());

        if (usuario.getPersona() instanceof Empleado) {
            Empleado empleadoExistente = (Empleado) usuario.getPersona();

            Empleado empleado = new Empleado();
            empleado.setCedula(empleadoExistente.getCedula());
            empleado.setNombre(empleadoExistente.getNombre());
            empleado.setApellido(empleadoExistente.getApellido());
            empleado.setFechaNacimiento(empleadoExistente.getFechaNacimiento());
            empleado.setCorreoElectronico(empleadoExistente.getCorreoElectronico());
            empleado.setTelefono(empleadoExistente.getTelefono());
            empleado.setGenero(empleadoExistente.getGenero());
            empleado.setDireccion(empleadoExistente.getDireccion());
            empleado.setCategoria(empleadoExistente.getCategoria());
            empleado.setHoraEntrada(empleadoExistente.getHoraEntrada());
            empleado.setHoraSalida(empleadoExistente.getHoraSalida());
            empleado.setHoraComida(empleadoExistente.getHoraComida());
            empleado.setDiaLibre(empleadoExistente.getDiaLibre());

            usuario.setPersona(empleado);
        } else {
            throw new IllegalArgumentException("La persona asociada no es de tipo Empleado.");
        }

        // Manejar los roles del usuario
        List<Rol> roles = new ArrayList<>();
        Rol rolEmpleado = rolRepository.findByDescripcion("ROLE_EMPLEADO");
        if (rolEmpleado == null) {
            rolEmpleado = new Rol("ROLE_EMPLEADO");
            rolRepository.save(rolEmpleado);
        }
        roles.add(rolEmpleado);
        usuario.setRoles(roles);

        // Encriptar la contraseña
        String encodedPassword = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(encodedPassword);

        // Guardar el usuario
        usuarioRepository.save(usuario);
    }*/

}
