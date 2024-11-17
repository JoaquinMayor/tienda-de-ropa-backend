package com.example.tienda.ropa.tienda_ropa.services.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.tienda.ropa.tienda_ropa.classes.elements.GeneratedCode;
import com.example.tienda.ropa.tienda_ropa.classes.elements.ResponseEntityGenerator;
import com.example.tienda.ropa.tienda_ropa.classes.enums.EmailEnum;
import com.example.tienda.ropa.tienda_ropa.classes.user.EmailDTO;
import com.example.tienda.ropa.tienda_ropa.entities.user.RestorePasswordData;
import com.example.tienda.ropa.tienda_ropa.entities.user.User;
import com.example.tienda.ropa.tienda_ropa.repositories.user.IRestorePasswordDataRepository;
import com.example.tienda.ropa.tienda_ropa.repositories.user.IUserRepository;

import jakarta.mail.MessagingException;

@Service
public class RestorePasswordService {

    @Autowired 
    private IUserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private IRestorePasswordDataRepository restorePasswordDataRepository;

    public ResponseEntity<?> sendEmailRestore(String email) throws MessagingException{
        Optional<User> userOptional = userRepository.findByEmail(email);
        
        if(userOptional.isPresent()){
            
            String code = GeneratedCode.generateUniqueCode();
            User user = userOptional.get();
            RestorePasswordData restore = new RestorePasswordData(code,userOptional.get());
            user.setRestorePassword(restore);
            userRepository.save(user);
            restorePasswordDataRepository.save(restore);
            EmailDTO emailDTO = new EmailDTO(email,EmailEnum.SubjectRestorePassword.getValue(),EmailEnum.MessageRestorePassword.getValue() + code);
            emailService.sendEmail(emailDTO);

            return ResponseEntityGenerator.genetateResponseEntity("Email enviado con éxito", 200, emailDTO);
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("Usuario no encontrado o inexistente", 400, null);
        }
    }
    
}
