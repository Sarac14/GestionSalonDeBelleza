package com.example.demo.controller;

import com.example.demo.Entidades.Usuario;
import com.example.demo.repositorios.UsuarioRepository;
import com.example.demo.servicios.JWTService;
import com.example.demo.servicios.JwtResponse;
import com.example.demo.servicios.UsuarioService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.Claims;

import java.security.SignatureException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario) {
        usuarioService.registrarNuevoUsuario(usuario);
        return ResponseEntity.ok("Usuario registrado con éxito");
    }

    /*@PostMapping("/actualizar/{cedula}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long cedula, @RequestBody Usuario usuarioActualizado, @RequestHeader("Authorization") String token) {

        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token faltante o en formato incorrecto");
        }
        try {
            Usuario usuarioExistente = usuarioService.findByCedula(cedula);
            System.out.println(usuarioExistente.getPersona().getNombre());
            usuarioService.actualizarUsuario(usuarioExistente, usuarioActualizado);
            return ResponseEntity.ok("Usuario actualizado con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
    }*/

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarUsuarios(@RequestParam String query) {
        List<Usuario> usuarios = usuarioService.buscarUsuarios(query);
        if (usuarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/actualizar/{cedula}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long cedula, @RequestBody Usuario usuarioActualizado, @RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token faltante o en formato incorrecto");
        }

        token = token.substring(7);

        try {
            Usuario usuarioExistente = usuarioService.findByCedula(cedula);
            if (usuarioExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }

            usuarioService.actualizarUsuario(usuarioExistente, usuarioActualizado);
            return ResponseEntity.ok("Usuario actualizado con éxito");
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expirado");
        } catch (UnsupportedJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token no soportado");
        } catch (MalformedJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token mal formado");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token vacío o nulo");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    @PostMapping("/cambiar-contrasena/{cedula}")
    public ResponseEntity<?> cambiarContraseña(@PathVariable Long cedula,
                                               @RequestBody Map<String, String> passwords,
                                               @RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token faltante o en formato incorrecto");
        }

        token = token.substring(7);

        try {
            String currentPassword = passwords.get("currentPassword");
            String newPassword = passwords.get("newPassword");

            if (currentPassword == null || newPassword == null) {
                return ResponseEntity.badRequest().body("Se requieren contraseña actual y nueva contraseña");
            }

            usuarioService.changePassword(cedula, currentPassword, newPassword);
            return ResponseEntity.ok("Contraseña cambiada con éxito");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> obtenerDatosUsuario(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                Claims claims = jwtService.extractAllClaims(token);
                String username = claims.get("username", String.class);

                Usuario usuario = usuarioService.findByUsername(username);
                if (usuario != null) {
                    return ResponseEntity.ok(usuario);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token faltante o en formato incorrecto");
        }
    }

    @GetMapping("/")
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        Usuario usuario = usuarioService.findByUsername(username);
        if (usuario == null) {
            System.out.println("User not found for username: " + username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nombre de usuario o contraseña incorrectos");
        }

        System.out.println("(********************************************************)");
        System.out.println(usuario.getUsername());
        System.out.println("(********************************************************)");
        System.out.println(usuario.getPersona().getNombre());

        boolean autenticado = usuarioService.autenticarUsuario(username, password);
        if (autenticado) {
            String token = jwtService.generateJwt(usuario);
            return ResponseEntity.ok(new JwtResponse(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nombre de usuario o contraseña incorrectos");
        }
    }
}
