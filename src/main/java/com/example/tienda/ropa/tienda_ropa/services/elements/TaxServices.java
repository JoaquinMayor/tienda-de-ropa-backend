package com.example.tienda.ropa.tienda_ropa.services.elements;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tienda.ropa.tienda_ropa.classes.elements.ResponseEntityGenerator;
import com.example.tienda.ropa.tienda_ropa.entities.clothe.ClotheSold;
import com.example.tienda.ropa.tienda_ropa.entities.elements.Tax;
import com.example.tienda.ropa.tienda_ropa.entities.user.User;
import com.example.tienda.ropa.tienda_ropa.repositories.clothe.IClothesSoldRepository;
import com.example.tienda.ropa.tienda_ropa.repositories.elements.ITaxRepository;
import com.example.tienda.ropa.tienda_ropa.repositories.user.IUserRepository;

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
            Tax newTax = taxMapper(tax, user); 
            Tax saveTax = taxRepository.save(newTax);
            user.setTax(newTax);
            userRepository.save(user); 
            return ResponseEntityGenerator.genetateResponseEntity("Factura creada con éxito", 201, saveTax);
        }
        return ResponseEntityGenerator.genetateResponseEntity("Usuario No encontrado", 404, "Usuario No encontrado");
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

    public Tax taxMapper(Tax tax, User user) {
        Tax newTax = new Tax();
        newTax.setId(UUID.randomUUID().toString());
        newTax.setCode(tax.getCode());
        newTax.setDate(tax.getDate());
        newTax.setPrice(tax.getPrice());
        newTax.setTravelCost(tax.getTravelCost());
        newTax.setAdress(tax.getAdress());
        newTax.setUser(user);

        taxRepository.save(newTax);
        tax.getClothes().forEach(clothe -> {
            ClotheSold newClothe = mapperClotheSold(clothe, newTax);
            newTax.setClothe(newClothe);
        });
        return newTax;

    }

    public ClotheSold mapperClotheSold(ClotheSold clothe, Tax tax) {
        ClotheSold newClothe = new ClotheSold();
        newClothe.setId(UUID.randomUUID().toString());
        newClothe.setCode(clothe.getCode());
        newClothe.setCant(clothe.getCant());
        newClothe.setName(clothe.getName());
        newClothe.setPrice(clothe.getPrice());
        newClothe.setDescription(clothe.getDescription());
        newClothe.setGenericType(clothe.getGenericType());
        newClothe.setPublication(clothe.getPublication());
        newClothe.setTax(tax);
        newClothe.setSize(clothe.getSize());
        newClothe.setSpecificType(clothe.getSpecificType());

        clothesRepository.save(newClothe);
        return newClothe;
    }


}
