package com.example.tienda.ropa.tienda_ropa.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tienda.ropa.tienda_ropa.classes.user.RestorePasswordDataDTO;
import com.example.tienda.ropa.tienda_ropa.services.user.RestorePasswordService;

import jakarta.mail.MessagingException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/restore/password")
public class PasswordController {

    @Autowired
    private RestorePasswordService restorePasswordService;


    @GetMapping("/{email}")
    public ResponseEntity<?> sendEmailPassword(@PathVariable String email) throws MessagingException{
        return this.restorePasswordService.sendEmailRestore(email);
    }

    @PostMapping("/change")
    public ResponseEntity<?> changuePassword(@RequestBody RestorePasswordDataDTO restore){
        return this.restorePasswordService.changePassword(restore);
    }
}
