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
    public ResponseEntity<?> save(String idUser, Tax tax){
        Optional<User> optionalUser = userRepository.findById(idUser);
        if(optionalUser.isPresent()){
            Tax newTax = new Tax();
            User user = optionalUser.orElseThrow();
            user.setTax(tax);
            newTax.setUser(user);

            newTax.setCode(tax.getCode());
            newTax.setDate(tax.getDate());
            newTax.setPrice(tax.getPrice());
            newTax.setTravelCost(tax.getTravelCost());
            newTax.setAdress(tax.getAdress());

            tax.getClothes().forEach(clothe -> {
                Optional<ClotheSold> optionalClote = clothesRepository.findById(clothe.getId());
                if(optionalClote.isPresent()){
                    ClotheSold clothesnew = optionalClote.get();
                    clothesnew.setTax(tax);
                    newTax.setClothe(clothesnew);
                    clothesRepository.save(clothesnew);
                }else{
                    clothe.setTax(tax);
                    newTax.setClothe(clothe);
                    clothesRepository.save(clothe);
                }

               });

            taxRepository.save(newTax);

            userRepository.save(user);
            return ResponseEntityGenerator.genetateResponseEntity("Factura creada con éxito",201,tax);
            
        }

       return ResponseEntityGenerator.genetateResponseEntity("Usuario no encontrado",404,null);
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
