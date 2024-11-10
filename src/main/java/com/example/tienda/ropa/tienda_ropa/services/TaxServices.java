package com.example.tienda.ropa.tienda_ropa.services;

import java.util.*;

import com.example.tienda.ropa.tienda_ropa.classes.ResponseEntityGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tienda.ropa.tienda_ropa.entities.ClotheSold;
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
    public ResponseEntity<?> save(String idUser, Tax tax) {
        Optional<User> optionalUser = userRepository.findById(idUser);
         
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Tax newTax = taxMapper(tax);
            newTax.setUser(user); 
            taxRepository.save(newTax); 

            tax.getClothes().forEach(clothe -> {
                ClotheSold newClothe = new ClotheSold();
                newClothe.setId(UUID.randomUUID().toString());
                newClothe.setCode(clothe.getCode());
                newClothe.setCant(clothe.getCant());
                newClothe.setName(clothe.getName());
                newClothe.setPrice(clothe.getPrice());
                newClothe.setDescription(clothe.getDescription());
                newClothe.setGenericType(clothe.getGenericType());
                newClothe.setPublication(clothe.getPublication());
                newClothe.setTax(newTax); 
                newClothe.setSize(clothe.getSize());
                newClothe.setSpecificType(clothe.getSpecificType());
                newTax.setClothe(newClothe);
                clothesRepository.save(newClothe);
            });

            user.setTax(newTax);
            userRepository.save(user); 
            return ResponseEntityGenerator.genetateResponseEntity("Factura creada con éxito", 201, newTax);
        }
        return ResponseEntityGenerator.genetateResponseEntity("Usuario No encontrado", 404, "Usuario No encontrado");
    }


    public Tax taxMapper(Tax tax){
            Tax newTax = new Tax();
            newTax.setId(UUID.randomUUID().toString());
            newTax.setCode(tax.getCode());
            newTax.setDate(tax.getDate());
            newTax.setPrice(tax.getPrice());
            newTax.setTravelCost(tax.getTravelCost());
            newTax.setAdress(tax.getAdress());
         return newTax;

    }

    @Transactional
    public ResponseEntity<?> findAll(Pageable pageable){

        Page<Tax> taxs = taxRepository.findAll(pageable);

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
    public ResponseEntity<?> findByDate(Date date, Pageable pageable){
        Page<Tax> taxs = taxRepository.findByDate(date, pageable);
        if(!taxs.isEmpty()){

            return ResponseEntityGenerator.genetateResponseEntity("Facturas encontradas con éxito",200,taxs);
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("Facturas no encontradas",404,null);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findByUserID(String userID, Pageable pageable){
        
        Page<Tax> taxs = taxRepository.findByUserId(userID, pageable);
        if(!taxs.isEmpty()){
            return ResponseEntityGenerator.genetateResponseEntity("Facturas encontradas con éxito", 200, taxs);
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("Facturas no encontradas",404,null);
        }
    }

}
