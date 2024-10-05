package com.example.tienda.ropa.tienda_ropa.repositories;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tienda.ropa.tienda_ropa.entities.Tax;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaxRepository extends JpaRepository<Tax,String>{

    Optional<Tax> findByCode(String code);

    Page<Tax> findByDate(Date date, Pageable pageable);

    Page<Tax> findByUserId(String userId, Pageable pageable);
}
