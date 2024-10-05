package com.example.tienda.ropa.tienda_ropa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tienda.ropa.tienda_ropa.entities.ClotheSold;
import org.springframework.stereotype.Repository;

@Repository
public interface IClothesSoldRepository extends JpaRepository<ClotheSold, String>{

}
