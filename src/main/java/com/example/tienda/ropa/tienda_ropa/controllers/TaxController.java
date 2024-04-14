package com.example.tienda.ropa.tienda_ropa.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tienda.ropa.tienda_ropa.Interface.IValidation;
import com.example.tienda.ropa.tienda_ropa.entities.Tax;
import com.example.tienda.ropa.tienda_ropa.services.TaxServices;


import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/taxs")
public class TaxController implements IValidation{

    @Autowired
    TaxServices taxServices;
    
    @GetMapping
    public ResponseEntity<?> findAll(){
        if(!taxServices.findAll().isEmpty()){
            return ResponseEntity.ok(taxServices.findAll());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> create(@PathVariable String idUser, @Valid @RequestBody Tax tax, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        
        return taxServices.save(idUser, tax);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> findByCode(@PathVariable String code){
        Optional<Tax> optionalTax = taxServices.findByCode(code);
        if(optionalTax.isPresent()){
            return ResponseEntity.ok(optionalTax.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/date")
    public ResponseEntity<?> findByDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date date){
        Set<Tax> taxs = taxServices.findByDate(date);
        if(!taxs.isEmpty()){
            return ResponseEntity.ok(taxs);
        }
        return ResponseEntity.notFound().build();
    }
    
    @Override
    public ResponseEntity<Map<String, String>> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err ->{ 
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors); 
    }
    
    
}
