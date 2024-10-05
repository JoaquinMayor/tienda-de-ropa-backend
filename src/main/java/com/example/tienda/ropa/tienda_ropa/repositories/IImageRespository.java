package com.example.tienda.ropa.tienda_ropa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tienda.ropa.tienda_ropa.entities.Image;
import org.springframework.stereotype.Repository;

@Repository
public interface IImageRespository extends JpaRepository<Image,String>{
    
}
