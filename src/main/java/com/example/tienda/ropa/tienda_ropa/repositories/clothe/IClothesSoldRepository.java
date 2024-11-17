package com.example.tienda.ropa.tienda_ropa.repositories.clothe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tienda.ropa.tienda_ropa.entities.clothe.ClotheSold;

@Repository
public interface IClothesSoldRepository extends JpaRepository<ClotheSold, String>{

}
