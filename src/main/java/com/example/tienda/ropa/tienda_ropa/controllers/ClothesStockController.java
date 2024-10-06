package com.example.tienda.ropa.tienda_ropa.controllers;

import java.util.HashMap;
import java.util.Map;

import com.example.tienda.ropa.tienda_ropa.classes.ClothePubli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import com.example.tienda.ropa.tienda_ropa.services.ClothesStockServices;

import jakarta.validation.Valid;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/clothes")
public class ClothesStockController implements IValidation{

    @Autowired
    private ClothesStockServices clothesService;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam int page, @RequestParam int cant){
        final Pageable pageable = PageRequest.of(page, cant, Sort.by(Sort.Direction.DESC, "publication"));
        return this.clothesService.findAll(pageable);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUpdate(@Valid @RequestBody ClothePubli clothe, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        return this.clothesService.save(clothe);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> findByCode(@PathVariable String code, @RequestParam int page, @RequestParam int cant){
        final Pageable pageable = PageRequest.of(page, cant, Sort.by(Sort.Direction.ASC, "code"));
        return this.clothesService.findByCode(code, pageable);
    }

    @GetMapping("/find")
    public ResponseEntity<?> findClothesByParameters(@RequestParam(required = false) String name, @RequestParam(required = false) String size, @RequestParam(required = false) Float priceMin, @RequestParam(required = false) Float priceMax, @RequestParam(required = false) String genericType, @RequestParam(required = false) String specificType, 
            @RequestParam int page, @RequestParam int cant){
        final Pageable pageable = PageRequest.of(page, cant, Sort.by(Sort.Direction.ASC, "publication"));
        return this.clothesService.findByParams(name, size, priceMin, priceMax, genericType, specificType, pageable);
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
