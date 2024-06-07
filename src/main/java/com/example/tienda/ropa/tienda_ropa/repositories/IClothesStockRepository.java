package com.example.tienda.ropa.tienda_ropa.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.tienda.ropa.tienda_ropa.entities.ClothesStock;

public interface IClothesStockRepository extends CrudRepository<ClothesStock,String>{
    

    @SuppressWarnings("null")
    Set<ClothesStock> findAll();

    @Query("select cs from ClothesStock cs left join fetch cs.images where cs.code = ?1")
    Set<ClothesStock> findByCode(String code);

    @Query("SELECT cs FROM ClothesStock cs WHERE " +
    "(:name IS NULL OR :name = '' OR cs.name LIKE %:name%) AND " +
    "(:size IS NULL OR :size = '' OR cs.size = :size) AND " +
    "(:priceMin IS NULL OR :priceMin = 0 OR cs.price > :priceMin) AND " +
    "(:priceMax IS NULL OR :priceMax = 0 OR cs.price < :priceMax) AND " +
    "(:genericType IS NULL OR :genericType = '' OR cs.genericType = :genericType) AND " +
    "(:specificType IS NULL OR :specificType = '' OR cs.specificType = :specificType)")
    Set<ClothesStock> findByParameters(
        @Param("name") String name,
        @Param("size") String size,
        @Param("priceMin") Float priceMin,
        @Param("priceMax") Float priceMax,
        @Param("genericType") String genericType,
        @Param("specificType") String specificType
);

    
}
