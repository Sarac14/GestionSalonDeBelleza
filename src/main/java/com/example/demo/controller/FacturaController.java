package com.example.demo.controller;

import com.example.demo.DTOs.FacturaDTO;
import com.example.demo.Entidades.*;
import com.example.demo.servicios.CitaService;
import com.example.demo.servicios.ClienteService;
import com.example.demo.servicios.EmpleadoService;
import com.example.demo.servicios.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

   /* @Autowired
    private DetalleService detalleService;*/

    @PostMapping("/crear")
    public ResponseEntity<Factura> crearFactura(@RequestBody FacturaDTO facturaRequest) throws ClassNotFoundException {
        Cliente cliente = clienteService.findById(facturaRequest.getClienteId());
        Empleado empleado = empleadoService.findById(facturaRequest.getEmpleadoId());
        Cita cita = citaService.findById(facturaRequest.getCitaId());
        Detalle detalle = cita.getServiciosCita().get(0).getDetalle();

        if (cliente == null || empleado == null || cita == null || detalle == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Factura factura = facturaService.crearFactura(cliente, empleado, cita, detalle,
                facturaRequest.getDescuento(),
                facturaRequest.getImpuesto(),
                facturaRequest.getMetodoPago());

        return new ResponseEntity<>(factura, HttpStatus.CREATED);
    }
}
