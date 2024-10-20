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
        System.out.println("Iniciando el proceso de guardar la factura para el usuario con ID:" + idUser);

        Tax newTax = taxMapper(idUser, tax);
        if (newTax == null) {
            System.out.println("No se pudo mapear el impuesto para el usuario con ID: {}" + idUser);
            return ResponseEntityGenerator.genetateResponseEntity("Error al crear la factura", 500, null);
        }

        tax.getClothes().forEach(clothe -> {
            ClotheSold newClothe = new ClotheSold();
            newClothe.setCant(clothe.getCant());
            newClothe.setName(clothe.getName());
            newClothe.setPrice(clothe.getPrice());
            newClothe.setDescription(clothe.getDescription());
            newClothe.setGenericType(clothe.getGenericType());
            newClothe.setTax(newTax);
            newClothe.setSize(clothe.getSize());
            newClothe.setSpecificType(clothe.getSpecificType());
            clothesRepository.save(newClothe);
            System.out.println("Guardado ClotheSold: {}" + newClothe);
        });

        taxRepository.save(newTax);
        System.out.println("Guardado Tax: {}" + newTax);

        return ResponseEntityGenerator.genetateResponseEntity("Factura creada con éxito", 201, newTax);
    }

    public Tax taxMapper(String idUser, Tax tax){
        Optional<User> optionalUser = userRepository.findById(idUser);
        if(optionalUser.isPresent()){
            Tax newTax = new Tax();
            User user = optionalUser.orElseThrow();
            newTax.setUser(user);

            newTax.setCode(tax.getCode());
            newTax.setDate(tax.getDate());
            newTax.setPrice(tax.getPrice());
            newTax.setTravelCost(tax.getTravelCost());
            newTax.setAdress(tax.getAdress());
            user.setTax(newTax);


            Tax finalTax = taxRepository.save(newTax);
            return finalTax;
        }
        return null;
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
