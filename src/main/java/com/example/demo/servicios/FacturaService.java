package com.example.demo.servicios;

import com.example.demo.Entidades.*;
import com.example.demo.repositorios.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    public Factura crearFactura(Cliente cliente, Empleado empleado, Cita cita, Detalle detalle,
                                float descuento, float impuesto, String metodoPago,  List<VentaProducto> ventasProductos) {
        Factura factura = new Factura();
        factura.setCliente(cliente);
        factura.setEmpleado(empleado);
        factura.setFechaEmision(new Date());
        factura.setDescuento(descuento);
        factura.setImpuesto(impuesto);
        factura.setMetodoPago(metodoPago);
        factura.setIdCita(cita.getId());
        detalle.getCita().setVigente(false);

        // Calcular subTotal y totalPagar basado en los detalles de la cita
        float subTotal = calcularSubTotal(detalle);
        factura.setSubTotal(subTotal);

        float totalPagar = calcularTotalPagar(subTotal, descuento, impuesto);

        // Calcular subtotal y total a pagar considerando las ventas de productos
        float subTotalProductos = 0;
        for (VentaProducto ventaProducto : ventasProductos) {
            subTotalProductos += ventaProducto.getCantidad() * ventaProducto.getProductoVenta().getPrecioVenta();
        }
        totalPagar += subTotalProductos;

        factura.setSubTotal(subTotal + subTotalProductos);
        factura.setTotalPagar(totalPagar);

        // Asignar el detalle a la factura
        factura.setDetalle(detalle);

        // Guardar la factura en la base de datos
        factura = facturaRepository.save(factura);

        return factura;
    }



    private float calcularSubTotal(Detalle detalle) {
        float subTotal = 0.0f;

        // Calcular subtotal por servicios
        for (ServicioCita servicioCita : detalle.getServiciosCita()) {
            subTotal += servicioCita.getServicio().getPrecioBase();
        }

        // Calcular subtotal por productos vendidos
        VentaProducto ventaProducto = detalle.getVentaProducto();
        if (ventaProducto != null) {
            subTotal += ventaProducto.getProductoVenta().getPrecioVenta();
        }

        return subTotal;
    }

    private float calcularTotalPagar(float subTotal, float descuento, float impuesto) {
        // Lógica para calcular el total a pagar
        float totalDescuento = subTotal * (descuento / 100);
        float totalImpuesto = subTotal * (impuesto / 100);
        return subTotal - totalDescuento + totalImpuesto;
    }
}
