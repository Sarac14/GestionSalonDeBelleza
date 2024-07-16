package com.example.demo.servicios;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static javax.crypto.Cipher.SECRET_KEY;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {
        String requestURI = request.getRequestURI();
        System.out.println("Processing request URI: " + requestURI);

        if (requestURI.equals("/usuario/registrar") || requestURI.equals("/usuario/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                Claims claims = jwtService.extractAllClaims(token);
                String username = claims.get("username", String.class);
                List<String> authorities = claims.get("authorities", List.class);

                System.out.println("Token: " + token);
                System.out.println("Username from token: " + username);
                System.out.println("Authorities from token: " + authorities);

                if (checkAccess(requestURI, authorities)) {
                    List<GrantedAuthority> grantedAuthorities = authorities.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    System.out.println("Access granted for URI: " + requestURI);
                    filterChain.doFilter(request, response);
                } else {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    System.out.println("Access denied for URI: " + requestURI);
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                System.out.println("Invalid token for URI: " + requestURI);
                e.printStackTrace();
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            System.out.println("Token missing or invalid format for URI: " + requestURI);
        }
    }

    private boolean checkAccess(String requestURI, List<String> authorities) {
        if (requestURI.startsWith("/notifications/")) {
            return authorities.contains("ROLE_ADMIN");
        }
        if (requestURI.startsWith("/admin/")) {
            return authorities.contains("ROLE_ADMIN");
        }
        if (requestURI.startsWith("/clientes/")) {
            return authorities.contains("ROLE_CLIENTE") || authorities.contains("ROLE_ADMIN");
        }
        if (requestURI.startsWith("/cita/")) {
            return authorities.contains("ROLE_CLIENTE") || authorities.contains("ROLE_ADMIN");
        }
        if (requestURI.startsWith("/factura/")) {
            return authorities.contains("ROLE_CLIENTE") || authorities.contains("ROLE_ADMIN");
        }
        if (requestURI.startsWith("/productos")) {
            return authorities.contains("ROLE_ADMIN");
        }
        if (requestURI.startsWith("/proveedores")) {
            return authorities.contains("ROLE_ADMIN");
        }
        if (requestURI.startsWith("/productoProveedores")) {
            return authorities.contains("ROLE_ADMIN");
        }
        if (requestURI.startsWith("/materiaPrimas")) {
            return authorities.contains("ROLE_ADMIN");
        }
        if (requestURI.startsWith("/productoVenta")) {
            return authorities.contains("ROLE_ADMIN");
        }
        if (requestURI.startsWith("/ordenesCompra")) {
            return authorities.contains("ROLE_ADMIN");
        }
        if (requestURI.startsWith("/productoProveedor")) {
            return authorities.contains("ROLE_ADMIN");
        }
        if (requestURI.startsWith("/performance")) {
            return authorities.contains("ROLE_ADMIN");
        }
        if (requestURI.startsWith("/servicios")) {
            return !authorities.isEmpty();
        }
        if (requestURI.startsWith("/empleados")) {
            return !authorities.isEmpty();
        }
        if (requestURI.startsWith("/usuario")) {
            return !authorities.isEmpty();
        }
        return false;
    }
}
