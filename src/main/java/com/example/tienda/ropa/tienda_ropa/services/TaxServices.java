package com.example.tienda.ropa.tienda_ropa.services;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private IClothesSoldRepository clothersRepository;
    
    @Transactional
    public ResponseEntity<?> save(String idUser, Tax tax){
        Optional<User> optionalUser = userRepository.findById(idUser);
        if(optionalUser.isPresent()){
            User user = optionalUser.orElseThrow();
            user.setTaxs(tax);
            tax.setUser(user);
            userRepository.save(user);
            tax.getClothes().forEach(clothe -> clothersRepository.save(clothe));
            taxRepository.save(tax);
            ResponseEntity.status(HttpStatus.CREATED).build();
            
        }
       return ResponseEntity.notFound().build();
    }

    @Transactional
    public Set<Tax> findAll(){
        return taxRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Tax> findByCode(String code){
        return taxRepository.findByCode(code);
    }

    @Transactional(readOnly = true)
    public Set<Tax> findByDate(Date date){
        return taxRepository.findByDate(date);
    }

}
