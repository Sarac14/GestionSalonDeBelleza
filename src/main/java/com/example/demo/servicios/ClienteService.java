package com.example.demo.servicios;

import com.example.demo.Entidades.Cliente;
import com.example.demo.Entidades.Persona;
import com.example.demo.repositorios.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public void crearCliente(Persona persona) {
        Cliente cliente = new Cliente(
                persona.getCedula(),
                persona.getNombre(),
                persona.getApellido(),
                persona.getFechaNacimiento(),
                persona.getCorreoElectronico(),
                persona.getTelefono()
        );
        clienteRepository.save(cliente);
    }

    public Cliente findById(Long clienteId) throws ClassNotFoundException {
        return clienteRepository.findById(Math.toIntExact(clienteId))
                .orElseThrow(() -> new ClassNotFoundException("Cliente no encontrado con ID: " + clienteId));
    }


}