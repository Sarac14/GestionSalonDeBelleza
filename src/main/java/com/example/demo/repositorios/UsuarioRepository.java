package com.example.demo.repositorios;

import com.example.demo.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    Usuario findUsuarioByUsernameAndPassword(String username, String password);

    Usuario findByUsername(String username);

    Usuario findByPersona_Cedula(Long cedula);

    @Query("SELECT u.persona.correoElectronico FROM Usuario u JOIN u.roles r WHERE r.descripcion = 'ROLE_CLIENTE'")
    List<String> findAllClientEmails();

    @Query("SELECT u.persona.correoElectronico FROM Usuario u JOIN u.roles r WHERE r.descripcion = 'ROLE_EMPLEADO'")
    List<String> findAllEmployeeEmails();

    @Query("SELECT u.persona.correoElectronico FROM Usuario u")
    List<String> findAllUserEmails();

    List<Usuario> findByPersonaCedulaOrUsernameContaining(Long cedula, String username);

}