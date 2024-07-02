package com.example.demo.controller;

import com.example.demo.Entidades.Cliente;
import com.example.demo.repositorios.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/")
    public List<Cliente> obtenerServicios() {
        return clienteRepository.findAll();
    }

    @GetMapping("/cliente/{id}")
    public Cliente obtenerClientePorId(@PathVariable Long id) {
        return clienteRepository.findById(Math.toIntExact(id)).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado con ID: " + id));
    }

    @GetMapping("/clienteCedula/{cedula}")
    public Cliente obtenerClientePorCedula(@PathVariable Long cedula) {
        return clienteRepository.findFirstByCedula(cedula).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado con cédula: " + cedula));
    }

}