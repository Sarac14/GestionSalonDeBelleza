package com.example.demo.servicios;

import com.example.demo.Entidades.ServicioCita;
import com.example.demo.repositorios.ServicioCitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioCitaService {
    @Autowired
    private ServicioCitaRepository servicioCitaRepository;

    public List<ServicioCita> findByCitaId(Long citaId) {
        return servicioCitaRepository.findByCitaId(citaId);
    }

}
