package com.example.demo.controller;

import com.example.demo.DTOs.EmailDTO;
import com.example.demo.servicios.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDTO emailDTO) {
        try {
            emailService.sendEmail(emailDTO);
            return ResponseEntity.ok("Email enviado con éxito");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al enviar el email: " + e.getMessage());
        }
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<String>> getClientEmails() {
        return ResponseEntity.ok(emailService.getAllClientEmails());
    }

    @GetMapping("/empleados")
    public ResponseEntity<List<String>> getEmployeeEmails() {
        return ResponseEntity.ok(emailService.getAllEmployeeEmails());
    }

    @GetMapping("/all")
    public ResponseEntity<List<String>> getAllUserEmails() {
        return ResponseEntity.ok(emailService.getAllUserEmails());
    }
}