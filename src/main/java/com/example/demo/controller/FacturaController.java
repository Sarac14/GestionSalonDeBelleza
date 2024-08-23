package com.example.demo.controller;

import com.example.demo.DTOs.FacturaDTO;
import com.example.demo.DTOs.VentaProductoDTO;
import com.example.demo.Entidades.*;
import com.example.demo.repositorios.FacturaRepository;
import com.example.demo.servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/factura")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private CitaService citaService;

    @Autowired
    private ProductoVentaService productoVentaService;

    @Autowired
    private FacturaRepository facturaRepository;

   /* @Autowired
    private DetalleService detalleService;*/

    @PostMapping("/crear")
    public ResponseEntity<Factura> crearFactura(@RequestBody FacturaDTO facturaRequest) throws ClassNotFoundException {
        Cliente cliente = clienteService.findById(facturaRequest.getClienteId());
        Empleado empleado = empleadoService.findById(facturaRequest.getEmpleadoId());
        Cita cita = citaService.findById(facturaRequest.getCitaId());
        Detalle detalle = cita.getServiciosCita().get(0).getDetalle();

        List<VentaProductoDTO> ventasProductos = facturaRequest.getVentasProductos();


        if (cliente == null || empleado == null || cita == null || detalle == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<VentaProducto> ventasProductosEntity = new ArrayList<>();
        for (VentaProductoDTO ventaProductoDTO : facturaRequest.getVentasProductos()) {
            VentaProducto ventaProducto = new VentaProducto();
            ventaProducto.setProductoVenta(productoVentaService.findById(ventaProductoDTO.getProductoVentaId()));
            ventaProducto.setCantidad(ventaProductoDTO.getCantidad());
            ventasProductosEntity.add(ventaProducto);
        }

        Factura factura = facturaService.crearFactura(cliente, empleado, cita, detalle, facturaRequest.getDescuento(), facturaRequest.getImpuesto(), facturaRequest.getMetodoPago(), ventasProductosEntity);

        factura.setVentasProductos(ventasProductosEntity);

        factura = facturaRepository.save(factura);

        return new ResponseEntity<>(factura, HttpStatus.CREATED);
    }
}
