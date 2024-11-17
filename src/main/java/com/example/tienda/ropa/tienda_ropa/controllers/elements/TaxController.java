package com.example.tienda.ropa.tienda_ropa.controllers.elements;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import com.example.tienda.ropa.tienda_ropa.entities.elements.Tax;
import com.example.tienda.ropa.tienda_ropa.services.elements.TaxServices;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/taxs")
public class TaxController implements IValidation{

    @Autowired
    TaxServices taxServices;
    
    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam int page, @RequestParam int cant){
        final Pageable pageable = PageRequest.of(page, cant, Sort.by(Sort.Direction.ASC, "id"));
        return this.taxServices.findAll(pageable);
    }

    @GetMapping("find/{idUser}")
    public ResponseEntity<?> findTaxByUser(@PathVariable String idUser, @RequestParam int page,
            @RequestParam int cant){
        final Pageable pageable = PageRequest.of(page, cant, Sort.by(Sort.Direction.ASC, "id"));
        return taxServices.findByUserID(idUser, pageable);
    }

    @PostMapping(path = "/{idUser}", consumes = "application/json;charset=UTF-8")
    public ResponseEntity<?> create(@PathVariable String idUser, @Valid @RequestBody Tax tax, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        
        return taxServices.save(idUser, tax);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> findByCode(@PathVariable String code){
        return this.taxServices.findByCode(code);
    }

    @GetMapping("/date")
    public ResponseEntity<?> findByDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date date, 
            @RequestParam int page, @RequestParam int cant){
        final Pageable pageable = PageRequest.of(page, cant, Sort.by(Sort.Direction.ASC, "id"));
        return this.taxServices.findByDate(date, pageable);
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
