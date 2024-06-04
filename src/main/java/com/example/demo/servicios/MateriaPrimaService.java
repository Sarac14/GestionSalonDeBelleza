package com.example.demo.servicios;

import com.example.demo.Entidades.MateriaPrima;
import com.example.demo.Entidades.Producto;
import com.example.demo.repositorios.MateriaPrimaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MateriaPrimaService {

    @Autowired
    MateriaPrimaRepository materiaPrimaRepository;

    public List<MateriaPrima> getAllMateriaPrima() {
        return materiaPrimaRepository.findAll();
    }

    public Optional<MateriaPrima> getProductoById(Long id) {
        return materiaPrimaRepository.findById(id);
    }

    public MateriaPrima saveProducto(MateriaPrima producto) {
        return materiaPrimaRepository.save(producto);
    }

    public void deleteProducto(Long id) {
        materiaPrimaRepository.deleteById(id);
    }
}
