package com.example.demo.controller;

import com.example.demo.DTOs.FacturaDTO;
import com.example.demo.DTOs.VentaProductoDTO;
import com.example.demo.Entidades.*;
import com.example.demo.repositorios.FacturaRepository;
import com.example.demo.servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/")
    public List<Factura> obtenerFacturas() {
        facturaService.imprimirConsola();
        return facturaRepository.findAll();
    }


    @PostMapping("/crear")
    public ResponseEntity<Factura> crearFactura(@RequestBody Factura facturaRequest) throws ClassNotFoundException {
        Cliente cliente = clienteService.findById(facturaRequest.getCliente().getId());
        Empleado empleado = empleadoService.findById(facturaRequest.getEmpleado().getId());
        Cita cita = citaService.findById(facturaRequest.getIdCita());
        Detalle detalle = cita.getServiciosCita().get(0).getDetalle();

        List<VentaProducto> ventasProductos = facturaRequest.getVentasProductos();


        if (cliente == null || empleado == null || cita == null || detalle == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<VentaProducto> ventasProductosEntity = new ArrayList<>();
        for (VentaProducto ventaProductoDTO : facturaRequest.getVentasProductos()) {
            VentaProducto ventaProducto = new VentaProducto();
            ventaProducto.setProductoVenta(productoVentaService.findById(ventaProductoDTO.getProductoVenta().getId()));
            ventaProducto.setCantidad(ventaProductoDTO.getCantidad());
            ventasProductosEntity.add(ventaProducto);
        }

        Factura factura = facturaService.crearFactura(cliente, empleado, cita, detalle, facturaRequest.getDescuento(), facturaRequest.getImpuesto(), facturaRequest.getMetodoPago(), ventasProductosEntity);

        factura.setVentasProductos(ventasProductosEntity);

        factura = facturaRepository.save(factura);

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
        ResponseEntity<Factura> responseEntity = new ResponseEntity<>(factura, HttpStatus.CREATED);

        System.out.println(responseEntity.getStatusCodeValue());

        return new ResponseEntity<>(factura, HttpStatus.CREATED);
    }
}
