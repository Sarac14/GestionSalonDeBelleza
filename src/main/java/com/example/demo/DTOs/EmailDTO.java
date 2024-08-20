package com.example.demo.DTOs;

import java.util.List;

public class EmailDTO {
    private List<String> destinatarios;
    private String asunto;
    private String mensaje;
    private String type;



    public List<String> getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(List<String> destinatarios) {
        this.destinatarios = destinatarios;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }
}
