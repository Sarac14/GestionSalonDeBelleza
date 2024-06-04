package com.example.demo.controller;

import com.example.demo.Entidades.MateriaPrima;
import com.example.demo.servicios.MateriaPrimaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/materiaPrimas")
public class MateriaPrimaController {

    @Autowired
    private MateriaPrimaService materiaPrimaService;
    @GetMapping("/")
    public List<MateriaPrima> getAllMateriaPrimas() {
        return materiaPrimaService.getAllMateriaPrima();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaPrima> getMateriaPrimaById(@PathVariable Long id) {
        Optional<MateriaPrima> materiaPrima = materiaPrimaService.getProductoById(id);
        return materiaPrima.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/nuevaMateriaPrima")
    public MateriaPrima createMateriaPrima(@RequestBody MateriaPrima materiaPrima) {
        return materiaPrimaService.saveProducto(materiaPrima);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MateriaPrima> updateMateriaPrima(@PathVariable Long id, @RequestBody MateriaPrima materiaPrimaDetails) {
        Optional<MateriaPrima> optionalMateriaPrima = materiaPrimaService.getProductoById(id);
        if (optionalMateriaPrima.isPresent()) {
            MateriaPrima materiaPrima = optionalMateriaPrima.get();
            materiaPrima.setMarca(materiaPrimaDetails.getMarca());
            materiaPrima.setNombre(materiaPrimaDetails.getNombre());
            materiaPrima.setDescripcion(materiaPrimaDetails.getDescripcion());
            materiaPrima.setCantidadStock(materiaPrimaDetails.getCantidadStock());
            materiaPrima.setPrecioCompra(materiaPrimaDetails.getPrecioCompra());
            materiaPrima.setCantidadMinima(materiaPrimaDetails.getCantidadMinima());
            materiaPrima.setUnidadMedida(materiaPrimaDetails.getUnidadMedida());
            materiaPrima.setCantidadDeUsos(materiaPrimaDetails.getCantidadDeUsos());
            materiaPrima.setCantidadPorUsos(materiaPrimaDetails.getCantidadPorUsos());

            return ResponseEntity.ok(materiaPrimaService.saveProducto(materiaPrima));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMateriaPrima(@PathVariable Long id) {
        if (materiaPrimaService.getProductoById(id).isPresent()) {
            materiaPrimaService.deleteProducto(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
