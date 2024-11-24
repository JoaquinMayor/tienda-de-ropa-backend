package com.example.tienda.ropa.tienda_ropa.controllers.user;

import com.example.tienda.ropa.tienda_ropa.classes.elements.ResponseEntityGenerator;
import com.example.tienda.ropa.tienda_ropa.classes.user.EmailDTO;
import com.example.tienda.ropa.tienda_ropa.services.user.EmailService;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<?> sendEmail(@RequestBody EmailDTO email) throws MessagingException {

        emailService.sendEmail(email);
        return ResponseEntityGenerator.genetateResponseEntity("Email enviado", 200,null);
    }
}
