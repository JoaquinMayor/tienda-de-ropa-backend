package com.example.tienda.ropa.tienda_ropa.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tienda.ropa.tienda_ropa.entities.Tax;
import com.example.tienda.ropa.tienda_ropa.entities.User;
import com.example.tienda.ropa.tienda_ropa.repositories.IClothesSoldRepository;
import com.example.tienda.ropa.tienda_ropa.repositories.ITaxRepository;
import com.example.tienda.ropa.tienda_ropa.repositories.IUserRepository;

import javax.ws.rs.core.Response;

@Service
public class TaxServices {
    
    @Autowired 
    private ITaxRepository taxRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IClothesSoldRepository clothesRepository;
    
    @Transactional
    public ResponseEntity<?> save(String idUser, Tax tax){
        Map<String, Object> response = new HashMap<>();
        Optional<User> optionalUser = userRepository.findById(idUser);
        if(optionalUser.isPresent()){
            User user = optionalUser.orElseThrow();
            user.setTaxs(tax);
            tax.setUser(user);

            taxRepository.save(tax);
            tax.getClothes().forEach(clothe -> {
                clothe.setTax(tax);
                clothesRepository.save(clothe);});
            
            
            userRepository.save(user);

            response.put("tax", tax);
            response.put("messege", "Factura creado con éxito");
            response.put("status", 201);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        }
        response.put("messege", "Usuario no encontrado");
        response.put("status", 404);
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @Transactional
    public ResponseEntity<?> findAll(){
        Map<String, Object> response = new HashMap<>();
        Set<Tax> taxs = taxRepository.findAll();
        response.put("taxs", taxs);
        response.put("messege", "Facturas encontradas con éxito");
        response.put("status", 200);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findByCode(String code){

        Map<String, Object> response = new HashMap<>();
        Optional<Tax> taxOptional = taxRepository.findByCode(code);
        if(taxOptional.isPresent()){
            response.put("tax", taxOptional.get());
            response.put("messege", "Factura encontrado");
            response.put("status", 200);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            response.put("messege", "Factura no encontrado");
            response.put("status", 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findByDate(Date date){
        Map<String, Object> response = new HashMap<>();
        Set<Tax> taxs = taxRepository.findByDate(date);
        if(!taxs.isEmpty()){
            response.put("user", taxs);
            response.put("messege", "Facturas encontradas con éxito");
            response.put("status", 200);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            response.put("messege", "Facuras no encontradas");
            response.put("status", 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
