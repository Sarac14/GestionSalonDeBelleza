package com.example.demo.servicios;

import com.example.demo.Entidades.ProductoProveedor;
import com.example.demo.Entidades.ProductoProveedorId;
import com.example.demo.Entidades.ProductoVenta;
import com.example.demo.Entidades.Proveedor;
import com.example.demo.repositorios.ProductoVentaRepository;
import com.example.demo.repositorios.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class ProductoVentaService {

    @Autowired
    ProductoVentaRepository productoVentaRepository;

    @Autowired
    ProveedorRepository proveedorRepository;

    public List<ProductoVenta> getAllProductoVenta() {
        return productoVentaRepository.findAll();
    }

    public Optional<ProductoVenta> getProductoById(Long id) {
        return productoVentaRepository.findById(id);
    }

    public ProductoVenta saveProducto(ProductoVenta producto) {
        Set<ProductoProveedor> proveedoresProducto = new HashSet<>();

        for (ProductoProveedor productoProveedor : producto.getProveedoresProducto()) {
            Proveedor proveedor = proveedorRepository.findById(productoProveedor.getProveedor().getId())
                    .orElseThrow(() -> new ProviderNotFoundException("Proveedor not found"));

            ProductoProveedorId productoProveedorId = new ProductoProveedorId();
            productoProveedorId.setProductoId(producto.getId());
            productoProveedorId.setProveedorId(proveedor.getId());

            productoProveedor.setId(productoProveedorId);
            productoProveedor.setProveedor(proveedor);
            productoProveedor.setProducto(producto);
            proveedoresProducto.add(productoProveedor);
        }

        producto.setProveedoresProducto(proveedoresProducto);

        return productoVentaRepository.save(producto);
    }

    public void deleteProducto(Long id) {
        productoVentaRepository.deleteById(id);
    }


    public ProductoVenta findById(Long productoVentaId) {
        return productoVentaRepository.findById(productoVentaId).orElse(null);
    }
}
