package com.example.demo.controller;

import com.example.demo.Entidades.Rol;
import com.example.demo.Entidades.Usuario;
import com.example.demo.servicios.JWTService;
import com.example.demo.servicios.JwtResponse;
import com.example.demo.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario) {
        usuarioService.registrarNuevoUsuario(usuario);
        return ResponseEntity.ok("Usuario registrado con éxito");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        System.out.println("Username: " + username);  // Log the username
        System.out.println("Password: " + password);  // Log the password

        Usuario usuario = usuarioService.findByUsername(username);
        if (usuario == null) {
            System.out.println("User not found for username: " + username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nombre de usuario o contraseña incorrectos");
        }

        System.out.println("(********************************************************)");
        System.out.println(usuario.getUsername());
        System.out.println("(********************************************************)");
        System.out.println(usuario.getPersona().getNombre());

        boolean autenticado = usuarioService.autenticarUsuario(username,password);
        if (autenticado) {
            String token = jwtService.generateJwt(usuario);
            return ResponseEntity.ok(new JwtResponse(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nombre de usuario o contraseña incorrectos");
        }
    }


}
