package com.example.tienda.ropa.tienda_ropa.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.tienda.ropa.tienda_ropa.entities.Image;

public interface IImageRespository extends CrudRepository<Image,String>{
    
}
