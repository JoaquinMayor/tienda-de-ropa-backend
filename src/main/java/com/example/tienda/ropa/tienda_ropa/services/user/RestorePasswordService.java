package com.example.tienda.ropa.tienda_ropa.services.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.tienda.ropa.tienda_ropa.classes.elements.GeneratedCode;
import com.example.tienda.ropa.tienda_ropa.classes.elements.ResponseEntityGenerator;
import com.example.tienda.ropa.tienda_ropa.classes.enums.EmailEnum;
import com.example.tienda.ropa.tienda_ropa.classes.user.EmailDTO;
import com.example.tienda.ropa.tienda_ropa.classes.user.RestorePasswordDataDTO;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<?> sendEmailRestore(String email) throws MessagingException{
        Optional<User> userOptional = userRepository.findByEmail(email);
        
        if(userOptional.isPresent()){
            
            String code = GeneratedCode.generateUniqueCode();
            User user = userOptional.get();
            RestorePasswordData restore = new RestorePasswordData(code,userOptional.get());
            user.setRestorePassword(restore);
            userRepository.save(user);
            EmailDTO emailDTO = new EmailDTO(email,EmailEnum.SubjectRestorePassword.getValue(),EmailEnum.MessageRestorePassword.getValue() + code);
            emailService.sendEmail(emailDTO);

            return ResponseEntityGenerator.genetateResponseEntity("Email enviado con éxito", 200, emailDTO);
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("Usuario no encontrado o inexistente", 400, null);
        }
    }

    public ResponseEntity<?> changePassword(RestorePasswordDataDTO data){

        Optional<RestorePasswordData> restore = restorePasswordDataRepository.findByCodeAndUserEmail(data.getCode(), data.getEmail());

        if(restore.isPresent()){
            Optional<User> optionalUser = userRepository.findByEmail(data.getEmail());
            if(optionalUser.isPresent()){
                String passwordEncoded = passwordEncoder.encode(data.getPassword());
                User user = optionalUser.get();
                user.setPassword(passwordEncoded);
                userRepository.save(user);
                return ResponseEntityGenerator.genetateResponseEntity("Contraseña cambiada con éxito", 200, "Éxito");
            }else{
                return ResponseEntityGenerator.genetateResponseEntity("Usuario no encontrado", 404, null);
            }    
            
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("Email o Código ingresado es incorrecto", 400, null);
        }
    }
    
}
