package com.example.tienda.ropa.tienda_ropa.services;

import java.util.*;

import com.example.tienda.ropa.tienda_ropa.classes.ResponseEntityGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tienda.ropa.tienda_ropa.entities.Tax;
import com.example.tienda.ropa.tienda_ropa.entities.User;
import com.example.tienda.ropa.tienda_ropa.repositories.IClothesSoldRepository;
import com.example.tienda.ropa.tienda_ropa.repositories.ITaxRepository;
import com.example.tienda.ropa.tienda_ropa.repositories.IUserRepository;

@Service
public class TaxServices {
    
    @Autowired 
    private ITaxRepository taxRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IClothesSoldRepository clothesRepository;
    
    @Transactional
    public ResponseEntity<?> save(Long idUser, Tax tax){
        Optional<User> optionalUser = userRepository.findById(idUser);
        if(optionalUser.isPresent()){
            User user = optionalUser.orElseThrow();
            user.setTax(tax);
            tax.setUser(user);

            taxRepository.save(tax);
            tax.getClothes().forEach(clothe -> {
                clothe.setTax(tax);
                clothesRepository.save(clothe);});

            userRepository.save(user);
            return ResponseEntityGenerator.genetateResponseEntity("Factura creada con éxito",201,tax);
            
        }

       return ResponseEntityGenerator.genetateResponseEntity("Usuario no encontrado",404,null);
    }

    @Transactional
    public ResponseEntity<?> findAll(){

        Set<Tax> taxs = taxRepository.findAll();

        return ResponseEntityGenerator.genetateResponseEntity("Facturas encontradas con éxito",200,taxs);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findByCode(String code){

        Optional<Tax> taxOptional = taxRepository.findByCode(code);
        if(taxOptional.isPresent()){
            return ResponseEntityGenerator.genetateResponseEntity("Factura encontrado",200,taxOptional.orElseThrow());
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("Factura no encontrado",404,null);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findByDate(Date date){
        Set<Tax> taxs = taxRepository.findByDate(date);
        if(!taxs.isEmpty()){

            return ResponseEntityGenerator.genetateResponseEntity("Facturas encontradas con éxito",200,taxs);
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("Facturas no encontradas",404,null);
        }
    }

}
