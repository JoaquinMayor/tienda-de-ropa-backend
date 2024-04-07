package com.example.tienda.ropa.tienda_ropa.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.tienda.ropa.tienda_ropa.entities.ClothesStock;

public interface IClothesStockRepository extends CrudRepository<ClothesStock,String>{
    

    Set<ClothesStock> findAll();

    Optional<ClothesStock> findByCode(String code);

    @Query("SELECT cs FROM ClothesStock cs WHERE " +
    "(:code IS NULL OR :code = '' OR cs.code = :code) AND " +
    "(:name IS NULL OR :name = '' OR cs.name = :name) AND " +
    "(:size IS NULL OR :size = '' OR cs.size = :size) AND " +
    "(:price IS NULL OR :price = 0 OR cs.price = :price) AND " +
    "(:genericType IS NULL OR :genericType = '' OR cs.genericType = :genericType) AND " +
    "(:specificType IS NULL OR :specificType = '' OR cs.specificType = :specificType)")
    Set<ClothesStock> findByParameters(
        @Param("code") String code,
        @Param("name") String name,
        @Param("size") String size,
        @Param("price") Float price,
        @Param("genericType") String genericType,
        @Param("specificType") String specificType
);

    
}
