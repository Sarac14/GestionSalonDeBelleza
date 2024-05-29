
package com.example.demo.servicios;

import com.example.demo.Entidades.Rol;
import com.example.demo.Entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class SeguridadServices implements UserDetailsService {
    @Autowired
    private final UsuarioService usuarioService;

    public SeguridadServices(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Autenticación JPA");
        Usuario user = usuarioService.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Usuario no existe.");
        }

        System.out.print("Intentando autenticar: " + username + ", Roles: ");

        Set<GrantedAuthority> roles = new HashSet<>();
        for (Rol rol : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(rol.getDescripcion()));
            System.out.print(rol.getDescripcion() + " ");
        }
        System.out.println();

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), true, true, true, true, grantedAuthorities);
    }

    public Usuario getAuthorizedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        return usuarioService.findByUsername(auth.getName());
    }
}