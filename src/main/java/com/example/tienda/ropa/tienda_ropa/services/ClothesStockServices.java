package com.example.tienda.ropa.tienda_ropa.services;


import java.util.*;

import com.example.tienda.ropa.tienda_ropa.classes.ResponseEntityGenerator;
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
       ClotheStock clotheStock = clothesRepository.save(clothe);
       
        clothe.getImages().forEach(image-> {
            image.setClotheStock(clothe);
            imageRespository.save(image);});

        return ResponseEntityGenerator.genetateResponseEntity("Prenda creada con éxito",201,clotheStock);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll(){
        Set<ClotheStock> clothes = clothesRepository.findAll();
        if(!clothes.isEmpty()) {
            return ResponseEntityGenerator.genetateResponseEntity("Prendas encontradas con éxito",200,clothes);
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("No se encontraron prendas",404,null);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findByCode(String code){
        Set<ClotheStock> clothes = clothesRepository.findByCode(code);
        if(!clothes.isEmpty()){
            return ResponseEntityGenerator.genetateResponseEntity("Prendas encontradas con éxito",200,clothes);
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("Prendas no encontradas",404,null);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findByParams(String name, String size, Float priceMin, Float priceMax, String genericType, String specificType){
        Set<ClotheStock> clothes = clothesRepository.findByParameters(name, size, priceMin,priceMax, genericType, specificType);
        if(!clothes.isEmpty()) {
            return ResponseEntityGenerator.genetateResponseEntity("Prendas encontradas con éxito",200,clothes);
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("No se encontraron prendas",404,null);
        }
    }

}
