package com.example.demo.servicios;

import com.example.demo.Entidades.OrdenCompra;
import com.example.demo.Entidades.Producto;
import com.example.demo.repositorios.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Configuration
@EnableScheduling
public class StockCheckerService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private OrdenCompraService ordenCompraService;

    @Scheduled(fixedDelay = 86400000) // Ejecutar cada 24 horas (en milisegundos)
    public void verificarStock() {
        List<Producto> productos = productoRepository.findAll();
        for (Producto producto : productos) {
            if (producto.getCantidadStock() <= producto.getCantidadMinima()) { // Si el stock es inferior al mínimo
                // Crear una orden de compra automáticamente para reponer el producto
                ordenCompraService.crearOrdenCompraAutomatica(producto.getId());
                System.out.println("Orden de Compra Automatica Realizada!!!!!!!!!!!!!!");
            }else{
                System.out.println("El producto aun tiene elementos suficioentes!!!!!!!!!!!!!!!!!");
            }
        }
    }
}
