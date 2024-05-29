package com.example.demo.config;

import com.example.demo.servicios.JwtAuthenticationFilter;
import com.example.demo.servicios.SeguridadServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;



import static java.security.KeyRep.Type.SECRET;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SeguridadServices seguridadServices;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(SeguridadServices seguridadServices, PasswordEncoder passwordEncoder) {
        this.seguridadServices = seguridadServices;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(seguridadServices).passwordEncoder(passwordEncoder);
        return auth.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/usuario/**"))
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/cita/**"))
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/cita/nuevaCita"))

                )
                .authorizeHttpRequests(authorization -> authorization
                        .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/empleado/**")).hasRole("EMPLEADO")
                        .requestMatchers(new AntPathRequestMatcher("/cliente/**")).hasRole("CLIENTE")
                        .requestMatchers(new AntPathRequestMatcher("/cita/**")).hasAnyAuthority("ROLE_ADMIN", "ROLE_CLIENTE")
                        .requestMatchers(new AntPathRequestMatcher("/usuario/registrar")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/usuario/login")).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}