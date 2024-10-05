package com.example.tienda.ropa.tienda_ropa.services;


import java.util.*;

import com.example.tienda.ropa.tienda_ropa.classes.ClothePubli;
import com.example.tienda.ropa.tienda_ropa.classes.EmailDTO;
import com.example.tienda.ropa.tienda_ropa.classes.ResponseEntityGenerator;
import com.example.tienda.ropa.tienda_ropa.entities.User;
import com.example.tienda.ropa.tienda_ropa.repositories.IUserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tienda.ropa.tienda_ropa.entities.ClotheStock;
import com.example.tienda.ropa.tienda_ropa.repositories.IClothesStockRepository;
import com.example.tienda.ropa.tienda_ropa.repositories.IImageRespository;


@Service
public class ClothesStockServices {

    @Autowired
    private IClothesStockRepository clothesRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private IImageRespository imageRespository;
    
    @Transactional
    public ResponseEntity<?> save(ClothePubli publi){
       ClotheStock clothe = publi.getClothe();
        ClotheStock clotheStock = clothesRepository.save(clothe);
       
        clothe.getImages().forEach(image-> {
            image.setClotheStock(clothe);
            imageRespository.save(image);});
        if(!publi.getMessage().isEmpty() && !publi.getSubject().isEmpty()){
            List<User> users = userRepository.findAll();
            users.forEach(user->{
                if(user.isVip() && user.getEnabled()){
                    EmailDTO email = new EmailDTO(user.getEmail(),publi.getSubject(),publi.getMessage());
                    System.out.println("Email " + email.getAddressee());
                    try {
                        emailService.sendEmail(email);
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        return ResponseEntityGenerator.genetateResponseEntity("Prenda creada con éxito",201,clotheStock);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll(Pageable pageable){
        Page<ClotheStock> clothes = clothesRepository.findAll(pageable);
        if(!clothes.isEmpty()) {
            return ResponseEntityGenerator.genetateResponseEntity("Prendas encontradas con éxito",200,clothes);
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("No se encontraron prendas",404,null);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findByCode(String code, Pageable pageable){
        Page<ClotheStock> clothes = clothesRepository.findByCode(code, pageable);
        if(!clothes.isEmpty()){
            return ResponseEntityGenerator.genetateResponseEntity("Prendas encontradas con éxito",200,clothes);
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("Prendas no encontradas",404,null);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findByParams(String name, String size, Float priceMin, Float priceMax, String genericType, String specificType, Pageable pageable){
        Page<ClotheStock> clothes = clothesRepository.findByParameters(name, size, priceMin,priceMax, genericType, specificType, pageable);
        if(!clothes.isEmpty()) {
            return ResponseEntityGenerator.genetateResponseEntity("Prendas encontradas con éxito",200,clothes);
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("No se encontraron prendas",404,null);
        }
    }

}
