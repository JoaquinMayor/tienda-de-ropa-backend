package com.example.tienda.ropa.tienda_ropa.controllers;

import com.example.tienda.ropa.tienda_ropa.classes.EmailDTO;
import com.example.tienda.ropa.tienda_ropa.classes.ResponseEntityGenerator;
import com.example.tienda.ropa.tienda_ropa.services.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping
    public ResponseEntity<?> sendEmail(@RequestBody EmailDTO email) throws MessagingException {

        emailService.sendEmail(email);
        return ResponseEntityGenerator.genetateResponseEntity("Email enviado", 200,null);
    }
}
