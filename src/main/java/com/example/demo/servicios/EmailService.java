package com.example.demo.servicios;

import com.example.demo.DTOs.EmailDTO;
import com.example.demo.Entidades.Factura;
import com.example.demo.repositorios.UsuarioRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine, UsuarioRepository usuarioRepository) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.usuarioRepository = usuarioRepository;
    }

    public void sendAppointmentConfirmationEmail(EmailDTO emailDTO) throws MessagingException {
        sendEmailWithTemplate(emailDTO, "confirmation_cita");
    }

    public void sendGeneralNotificationEmail(EmailDTO emailDTO) throws MessagingException {
        sendEmailWithTemplate(emailDTO, "notification_general");
    }

    public void sendExclusivaNotificationEmail(EmailDTO emailDTO) throws MessagingException {
        sendEmailWithTemplate(emailDTO, "notification_exclusiva");
    }

    void sendEmailWithTemplate(EmailDTO emailDTO, String templateName) throws MessagingException {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(emailDTO.getDestinatarios().toArray(new String[0]));
            helper.setSubject(emailDTO.getAsunto());

            Context context = new Context();
            context.setVariable("mensaje", emailDTO.getMensaje());
            String htmlContent = templateEngine.process(templateName, context);

            helper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new MessagingException("Error al enviar el correo: " + e.getMessage());
        }
    }
    public void sendFacturaEmail(EmailDTO emailDTO, Factura factura) throws MessagingException {
        Context context = new Context();
        context.setVariable("idFactura", factura.getId().toString());
        context.setVariable("nombreCliente", factura.getCliente().getNombre());
        context.setVariable("nombreEmpleado", factura.getEmpleado().getNombre());
        context.setVariable("fechaEmision", factura.getFechaEmision().toString());
        context.setVariable("totalPagar", String.format("%.2f", factura.getTotalPagar()));
        context.setVariable("mensaje", emailDTO.getMensaje());

        String htmlContent = templateEngine.process("factura_template", context);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(emailDTO.getDestinatarios().toArray(new String[0]));
        helper.setSubject(emailDTO.getAsunto());
        helper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);
    }


    public List<String> getAllClientEmails() {
        return usuarioRepository.findAllClientEmails();
    }

    public List<String> getAllEmployeeEmails() {
        return usuarioRepository.findAllEmployeeEmails();
    }

    public List<String> getAllUserEmails() {
        return usuarioRepository.findAllUserEmails();
    }

    public void sendFacturaEmail(EmailDTO emailDTO) throws MessagingException {
        sendEmailWithTemplate(emailDTO, "factura_template");
    }
}