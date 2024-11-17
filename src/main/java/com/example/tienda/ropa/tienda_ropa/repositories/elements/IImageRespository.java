package com.example.tienda.ropa.tienda_ropa.repositories.elements;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tienda.ropa.tienda_ropa.entities.elements.Image;

@Repository
public interface IImageRespository extends JpaRepository<Image,String>{
    
}
