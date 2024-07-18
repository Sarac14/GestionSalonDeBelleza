package com.example.demo.servicios;

import com.example.demo.Entidades.Cliente;
import com.example.demo.Entidades.VentaProducto;
import com.example.demo.performance.ProductSalesByEmployeeReport;
import com.example.demo.performance.SalesByCustomerReport;
import com.example.demo.performance.TopSalesByCustomerReport;
import com.example.demo.repositorios.ProductoRepository;
import com.example.demo.repositorios.ProductoVentaRepository;
import com.example.demo.repositorios.VentaProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SalesReportService {
    @Autowired
    private VentaProductoRepository ventaProductoRepository;

    @Autowired
    private ProductoVentaRepository productoVentaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    // Facturación de productos por empleado
    public List<ProductSalesByEmployeeReport> getProductSalesByEmployee(Long empleadoId, LocalDate startDate, LocalDate endDate) {
        List<VentaProducto> ventas = ventaProductoRepository.findByFechaBetween(startDate, endDate)
                .stream()
                .filter(vp -> vp.getEmpleado().getId().equals(empleadoId))
                .collect(Collectors.toList());

        return ventas.stream()
                .map(vp -> new ProductSalesByEmployeeReport(vp.getEmpleado().getId(), vp.getEmpleado().getNombre(), vp.getProductoVenta().getNombre(), vp.getCantidad(), vp.getProductoVenta().getPrecioVenta() * vp.getCantidad()))
                .collect(Collectors.toList());
    }


    // Ventas por cliente
    public List<SalesByCustomerReport> getSalesByCustomer(Long clienteId, LocalDate startDate, LocalDate endDate) {
        List<VentaProducto> ventas = ventaProductoRepository.findByDetalle_Cita_ClienteId(clienteId)
                .stream()
                .filter(vp -> !vp.getDetalle().getCita().getFecha().isBefore(startDate) && !vp.getDetalle().getCita().getFecha().isAfter(endDate))
                .collect(Collectors.toList());

        return ventas.stream()
                .map(vp -> new SalesByCustomerReport(vp.getDetalle().getCita().getCliente().getId(), vp.getDetalle().getCita().getCliente().getNombre(), vp.getProductoVenta().getNombre(), vp.getCantidad(), vp.getProductoVenta().getPrecioVenta() * vp.getCantidad()))
                .collect(Collectors.toList());
    }

    // Top 20 Ventas por cliente
    public List<TopSalesByCustomerReport> getTopSalesByCustomer(LocalDate startDate, LocalDate endDate) {
        List<VentaProducto> ventas = ventaProductoRepository.findByFechaBetween(startDate, endDate);

        Map<Long, Float> salesMap = new HashMap<>();

        for (VentaProducto venta : ventas) {
            Long clienteId = venta.getDetalle().getCita().getCliente().getId();
            float totalVenta = venta.getProductoVenta().getPrecioVenta() * venta.getCantidad();
            salesMap.put(clienteId, salesMap.getOrDefault(clienteId, 0.0f) + totalVenta);
        }

        return salesMap.entrySet().stream()
                .sorted((e1, e2) -> Float.compare(e2.getValue(), e1.getValue()))
                .limit(20)
                .map(entry -> {
                    Cliente cliente = ventaProductoRepository.findByDetalle_Cita_ClienteId(entry.getKey()).get(0).getDetalle().getCita().getCliente();
                    return new TopSalesByCustomerReport(cliente.getId(), cliente.getNombre(), entry.getValue());
                })
                .collect(Collectors.toList());
    }
}
