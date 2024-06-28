package com.example.tienda.ropa.tienda_ropa.services;


import java.util.*;

import com.example.tienda.ropa.tienda_ropa.entities.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tienda.ropa.tienda_ropa.entities.ClotheStock;
import com.example.tienda.ropa.tienda_ropa.repositories.IClothesStockRepository;
import com.example.tienda.ropa.tienda_ropa.repositories.IImageRespository;

import javax.ws.rs.core.Response;

@Service
public class ClothesStockServices {

    @Autowired
    private IClothesStockRepository clothesRepository;

    @Autowired
    private IImageRespository imageRespository;
    
    @Transactional
    public ResponseEntity<?> save(ClotheStock clothe){
        Map<String, Object> response = new HashMap<>();
       ClotheStock clotheStock = clothesRepository.save(clothe);
       
        clothe.getImages().forEach(image-> {
            image.setClotheStock(clothe);
            imageRespository.save(image);});

        response.put("clothe", clotheStock);
        response.put("messege", "Prenda creada con éxito");
        response.put("status", 201);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll(){
        Map<String, Object> response = new HashMap<>();
        Set<ClotheStock> clothes = clothesRepository.findAll();
        if(!clothes.isEmpty()) {
            response.put("clothes", clothes);
            response.put("messege", "Prendas encontradas con éxito");
            response.put("status", 200);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            response.put("messege", "No se encontraron prendas");
            response.put("status", 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findByCode(String code){
        Map<String, Object> response = new HashMap<>();
        Set<ClotheStock> clothes = clothesRepository.findByCode(code);
        if(!clothes.isEmpty()){
            response.put("clothes", clothes);
            response.put("messege", "Prendas encontradas con éxito");
            response.put("status", 200);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            response.put("messege", "Prendas no encontradas");
            response.put("status", 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findByParams(String name, String size, Float priceMin, Float priceMax, String genericType, String specificType){
        Map<String, Object> response = new HashMap<>();
        Set<ClotheStock> clothes = clothesRepository.findByParameters(name, size, priceMin,priceMax, genericType, specificType);
        if(!clothes.isEmpty()) {
            response.put("clothes", clothes);
            response.put("messege", "Prendas encontradas con éxito");
            response.put("status", 200);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            response.put("messege", "No se encontraron prendas");
            response.put("status", 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
