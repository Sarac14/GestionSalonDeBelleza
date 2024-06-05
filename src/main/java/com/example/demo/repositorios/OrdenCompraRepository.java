package com.example.demo.repositorios;

import com.example.demo.Entidades.OrdenCompra;
import com.example.demo.Entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long> {
    //boolean existsByProductoAndEstadoOrdenCompra(Producto producto, String estado);
}
