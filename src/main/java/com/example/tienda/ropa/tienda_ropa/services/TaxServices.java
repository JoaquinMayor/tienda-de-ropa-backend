package com.example.tienda.ropa.tienda_ropa.services;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tienda.ropa.tienda_ropa.entities.Tax;
import com.example.tienda.ropa.tienda_ropa.repositories.ITaxRepository;

@Service
public class TaxServices {
    
    @Autowired 
    private ITaxRepository taxRepository;
    
    @Transactional
    public Tax save(Tax tax){
        return taxRepository.save(tax);
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
