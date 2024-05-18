package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class pruebaTiempoEspera {
    @GetMapping("/index")
    public String inicio() {
        return "index";  // Asegúrate de que "index" sea el nombre del archivo HTML sin extensión.
    }
}
