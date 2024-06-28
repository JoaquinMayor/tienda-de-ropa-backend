package com.example.tienda.ropa.tienda_ropa.repositories;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.example.tienda.ropa.tienda_ropa.entities.Tax;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaxRepository extends CrudRepository<Tax,String>{

    @SuppressWarnings("null")
    Set<Tax> findAll();

    Optional<Tax> findByCode(String code);

    Set<Tax> findByDate(Date date);
}
