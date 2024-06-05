package com.example.demo.servicios;

import com.example.demo.Entidades.*;
import com.example.demo.repositorios.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrdenCompraService {

    @Autowired
    private static OrdenCompraRepository ordenCompraRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoOrdenCompraRepository productoOrdenCompraRepository;

    @Autowired
    private ProductoProveedorRepository productoProveedorRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;


    public OrdenCompra crearOrdenCompra(Long proveedorId, List<Long> productosIds, List<Integer> cantidades, Date fechaEntrega) {
        Proveedor proveedor = proveedorRepository.findById(proveedorId).orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        OrdenCompra ordenCompra = new OrdenCompra();
        ordenCompra.setFechaOrden(new Date());
        ordenCompra.setProveedor(proveedor);
        ordenCompra.setEstadoOrdenCompra("Pendiente");
        ordenCompra.setFechaEntrega(fechaEntrega);

        List<ProductoOrdenCompra> productosOrdenCompra = new ArrayList<>();
        float totalOrdenCompra = 0;

        for(int i =0; i<productosIds.size(); i++){
            Producto producto = productoRepository.findById(productosIds.get(i)).orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            ProductoOrdenCompra productoOrdenCompra = new ProductoOrdenCompra();
            productoOrdenCompra.setOrdenCompra(ordenCompra);
            productoOrdenCompra.setProductos(List.of(producto));
            productoOrdenCompra.setCantidad(cantidades.get(i));

            productosOrdenCompra.add(productoOrdenCompra);
            totalOrdenCompra += producto.getPrecioCompra() * cantidades.get(i);
        }

        ordenCompra.setProductos(productosOrdenCompra);
        ordenCompra.setTotalOrdenCompra(totalOrdenCompra);

        return ordenCompraRepository.save(ordenCompra);
    }

    public void crearOrdenCompraAutomatica(Long productoId) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        int cantidadAOrdenar = producto.getCantidadPedidoAutomatico();

        if (cantidadAOrdenar > 0) {
            Proveedor proveedor = obtenerProveedor(producto); // Ahora usa la función actualizada

            if (proveedor != null) {
                OrdenCompra ordenCompra = new OrdenCompra();
                ordenCompra.setFechaOrden(new Date());
                ordenCompra.setProveedor(proveedor);
                ordenCompra.setEstadoOrdenCompra("Pendiente");
                ordenCompra.setFechaEntrega(calcularFechaEntrega());
                ordenCompra.setTotalOrdenCompra(producto.getPrecioCompra() * cantidadAOrdenar);

                ProductoOrdenCompra productoOrdenCompra = new ProductoOrdenCompra();
                productoOrdenCompra.setProductos(List.of(producto));
                productoOrdenCompra.setCantidad(cantidadAOrdenar);
                productoOrdenCompra.setOrdenCompra(ordenCompra);
                ordenCompra.setProductos(List.of(productoOrdenCompra));

                ordenCompraRepository.save(ordenCompra);
            } else {
                throw new RuntimeException("No hay proveedores disponibles para el producto");
            }
        }
    }

    private Date calcularFechaEntrega() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        return calendar.getTime();
    }

    private Proveedor obtenerProveedor(Producto producto) {
        List<ProductoProveedor> proveedoresProducto = productoProveedorRepository.findByProductoId(producto.getId());

        if (!proveedoresProducto.isEmpty()) {
            // Simplemente devolvemos el primer proveedor de la lista
            return proveedoresProducto.get(0).getProveedor();
        }

        return null;
    }

    public static List<OrdenCompra> getAllOrdendesCompras() {
        return ordenCompraRepository.findAll();
    }
}
