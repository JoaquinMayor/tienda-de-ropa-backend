package com.example.tienda.ropa.tienda_ropa.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tienda.ropa.tienda_ropa.Interface.IValidation;
import com.example.tienda.ropa.tienda_ropa.entities.ClothesStock;
import com.example.tienda.ropa.tienda_ropa.services.ClothesStockServices;

import jakarta.validation.Valid;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/clothes")
public class ClothesStockController implements IValidation{

    @Autowired
    private ClothesStockServices clothesService;

    @GetMapping
    public ResponseEntity<?> findAll(){
        if(!clothesService.findAll().isEmpty()){
            return ResponseEntity.ok(clothesService.findAll());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createUpdate(@Valid @RequestBody ClothesStock clothe, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(clothesService.save(clothe));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> findByCode(@PathVariable String code){
        
        Optional<ClothesStock> optionalClothe = clothesService.findByCode(code);
        if(optionalClothe.isPresent()){
            return ResponseEntity.ok(optionalClothe.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("find/{name}/{size}/{priceMin}/{priceMax}/{genericType}/{specificType}")
    public ResponseEntity<?> findClothesByParameters(@PathVariable String name, @PathVariable String size, @PathVariable Float priceMin, @PathVariable Float priceMax, @PathVariable String genericType, @PathVariable String SpecificType){
        Set<ClothesStock> clothes = clothesService.findByParams(name, size, priceMin, priceMax, genericType, SpecificType);
        if(!clothes.isEmpty()){
            return ResponseEntity.ok(clothes);
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
