package com.example.demo.servicios;

import com.example.demo.DTOs.EmailDTO;
import com.example.demo.Entidades.*;
import com.example.demo.repositorios.FacturaRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private EmailService emailService;

    public void imprimirConsola(){
        List<Factura> facturas = facturaRepository.findAll();
        for (Factura fc: facturas) {
           System.out.println(fc.getCliente().nombre);
            System.out.println(fc.getEmpleado().nombre);
        }
    }
    public Factura crearFactura(Cliente cliente, Empleado empleado, Cita cita, Detalle detalle, float descuento, float impuesto, String metodoPago, List<VentaProducto> ventasProductos) throws MessagingException {
        Factura factura = new Factura();
        factura.setCliente(cliente);
        factura.setEmpleado(empleado);
        factura.setFechaEmision(new Date());
        factura.setDescuento(descuento);
        factura.setImpuesto(impuesto);
        factura.setMetodoPago(metodoPago);
        factura.setIdCita(cita.getId());

        cita.setVigente(false);

        // Asignar la factura a cada VentaProducto
        for (VentaProducto ventaProducto : ventasProductos) {
            ventaProducto.setFactura(factura);
        }

        // Guardar la Factura con las Ventas de Productos asociadas
        factura.setVentasProductos(ventasProductos);

        // Calcular subtotal y total a pagar considerando las ventas de productos
        float subTotalProductos = 0;
        float cant;
        for (VentaProducto ventaProducto : ventasProductos) {
            subTotalProductos += ventaProducto.getCantidad() * ventaProducto.getProductoVenta().getPrecioVenta();
            cant = ventaProducto.getProductoVenta().getCantidadStock() - ventaProducto.getCantidad();
            ventaProducto.getProductoVenta().setCantidadStock((int) cant);
        }

        float subTotal = calcularSubTotal(detalle) + subTotalProductos;
        float totalPagar = calcularTotalPagar(subTotal, descuento, impuesto);

        factura.setSubTotal(subTotal);
        factura.setTotalPagar(totalPagar);
        factura.setDetalle(detalle);

        return facturaRepository.save(factura);
    }


    public void enviarFacturaPorCorreo(Factura factura) throws MessagingException {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setDestinatarios(List.of(factura.getCliente().getCorreoElectronico()));
        emailDTO.setAsunto("Factura de su cita con ID: " + factura.getId());
        emailDTO.setMensaje("Gracias por su compra en Lina Beauty Place. A continuación, encontrará los detalles de su factura.");
        emailDTO.setType("factura"); // Si necesitas especificar un tipo

        emailService.sendFacturaEmail(emailDTO, factura);
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
