package com.example.tienda.ropa.tienda_ropa.services.user;

import com.example.tienda.ropa.tienda_ropa.classes.clothe.ClothePubli;
import com.example.tienda.ropa.tienda_ropa.classes.user.EmailDTO;
import com.example.tienda.ropa.tienda_ropa.entities.user.User;
import com.example.tienda.ropa.tienda_ropa.repositories.user.IUserRepository;

import jakarta.mail.MessagingException;

import jakarta.mail.internet.MimeMessage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    IUserRepository userRepository;

    public void sendEmail(EmailDTO email) throws MessagingException {
        try{
           MimeMessage message = javaMailSender.createMimeMessage();
           MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

           helper.setTo(email.getAddressee()); //Se pone el destinatario, se pueden poner varios
            helper.setSubject(email.getSubject()); // Se pone el asunto del email
           helper.setText(email.getMessage()); // Se pone el mensaje del email

           javaMailSender.send(message);
        }catch (Exception e) {
            System.out.println("Entro al error");
            e.printStackTrace();
        }
    }


    public void sendEmailVips(ClothePubli publi){
        if(!publi.getMessage().isEmpty() && !publi.getSubject().isEmpty()){
            List<User> users = userRepository.findVips();
            users.forEach(user->{
                if(user.getEnabled()){
                    EmailDTO email = new EmailDTO(user.getEmail(),publi.getSubject(),publi.getMessage());
                    try {
                        sendEmail(email);
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }
}
