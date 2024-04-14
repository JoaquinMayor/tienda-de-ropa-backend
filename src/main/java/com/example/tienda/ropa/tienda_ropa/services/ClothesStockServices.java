package com.example.tienda.ropa.tienda_ropa.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tienda.ropa.tienda_ropa.entities.ClothesStock;
import com.example.tienda.ropa.tienda_ropa.repositories.IClothesStockRepository;

@Service
public class ClothesStockServices {

    @Autowired
    private IClothesStockRepository clothesRepository;
    
    @Transactional
    public ClothesStock save(ClothesStock clothe){
        return clothesRepository.save(clothe);
    }

    @Transactional(readOnly = true)
    public Set<ClothesStock> findAll(){
        return clothesRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<ClothesStock> findByCode(String code){
        return clothesRepository.findByCode(code);
    }

    @Transactional(readOnly = true)
    public Set<ClothesStock> findByParams(String name, String size, Float priceMin, Float priceMax, String genericType, String specificType){
        return clothesRepository.findByParameters(name, size, priceMin,priceMax, genericType, specificType);
    }

}
