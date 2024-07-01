package com.example.demo.repositorios;

import com.example.demo.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    Usuario findUsuarioByUsernameAndPassword(String username, String password);

    Usuario findByUsername(String username);

    Usuario findByPersona_Cedula(Long cedula);
}