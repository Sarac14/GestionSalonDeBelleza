package com.example.demo.servicios;

import com.example.demo.Entidades.OrdenCompra;
import com.example.demo.Entidades.Producto;
import com.example.demo.Entidades.VentaProducto;
import com.example.demo.performance.AlertProductReport;
import com.example.demo.performance.CurrentInventoryReport;
import com.example.demo.performance.PurchaseOrderReport;
import com.example.demo.performance.TopProductReport;
import com.example.demo.repositorios.OrdenCompraRepository;
import com.example.demo.repositorios.ProductoRepository;
import com.example.demo.repositorios.VentaProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InventoryReportService {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private VentaProductoRepository ventaProductoRepository;

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    public List<CurrentInventoryReport> getCurrentInventoryReport() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(p -> new CurrentInventoryReport(p.getId(), p.getNombre(), p.getCantidadStock(), p.getCantidadMinima()))
                .collect(Collectors.toList());
    }

    public List<TopProductReport> getTopProductsReport(LocalDate startDate, LocalDate endDate) {
        List<VentaProducto> ventas = ventaProductoRepository.findByFechaBetween(startDate, endDate);
        Map<Long, Integer> salesCountMap = new HashMap<>();

        for (VentaProducto venta : ventas) {
            Long productId = venta.getProductoVenta().getId();
            salesCountMap.put(productId, salesCountMap.getOrDefault(productId, 0) + venta.getCantidad());
        }

        return salesCountMap.entrySet().stream()
                .map(entry -> new TopProductReport(entry.getKey(), productoRepository.findById(entry.getKey()).get().getNombre(), entry.getValue()))
                .sorted((p1, p2) -> Integer.compare(p2.getSalesCount(), p1.getSalesCount()))
                .collect(Collectors.toList());
    }

    public List<PurchaseOrderReport> getPurchaseOrderReport(LocalDate startDate, LocalDate endDate) {
        List<OrdenCompra> ordenes = ordenCompraRepository.findByFechaOrdenBetween(startDate, endDate);
        return ordenes.stream()
                .map(o -> new PurchaseOrderReport(o.getId(), o.getFechaOrden(), o.getProveedor().getNombreContacto(), o.getTotalOrdenCompra(), o.getEstadoOrdenCompra()))
                .collect(Collectors.toList());
    }

    public List<AlertProductReport> getAlertProductReport() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .filter(p -> p.getCantidadStock() <= p.getCantidadMinima())
                .map(p -> new AlertProductReport(p.getId(), p.getNombre(), p.getCantidadStock(), p.getCantidadMinima()))
                .collect(Collectors.toList());
    }
}
