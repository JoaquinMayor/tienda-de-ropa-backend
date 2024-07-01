package com.example.tienda.ropa.tienda_ropa.services;

import com.example.tienda.ropa.tienda_ropa.classes.EmailDTO;
import jakarta.mail.MessagingException;


import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendEmail(EmailDTO email) throws MessagingException {
        try{


           MimeMessage message = javaMailSender.createMimeMessage();
           MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

           helper.setTo(email.getAddressee()); //Se pone el destinatario, se pueden poner varios
            helper.setSubject(email.getSubject()); // Se pone el asunto del email
           helper.setText(email.getMessage()); // Se pone el mensaje del email


           javaMailSender.send(message);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
