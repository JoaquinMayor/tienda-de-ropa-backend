package com.example.tienda.ropa.tienda_ropa.repositories.clothe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.tienda.ropa.tienda_ropa.entities.clothe.ClotheStock;

@Repository
public interface IClothesStockRepository extends JpaRepository<ClotheStock,String>{
    

    @Query("select cs from ClotheStock cs left join fetch cs.images where cs.code = ?1")
    Page<ClotheStock> findByCode(String code, Pageable pageable);

    @Query("SELECT cs FROM ClotheStock cs WHERE " +
    "(:name IS NULL OR :name = '' OR cs.name LIKE %:name%) AND " +
    "(:size IS NULL OR :size = '' OR cs.size = :size) AND " +
    "(:priceMin IS NULL OR :priceMin = 0 OR cs.price > :priceMin) AND " +
    "(:priceMax IS NULL OR :priceMax = 0 OR cs.price < :priceMax) AND " +
    "(:genericType IS NULL OR :genericType = '' OR cs.genericType = :genericType) AND " +
    "(:specificType IS NULL OR :specificType = '' OR cs.specificType = :specificType)")
    Page<ClotheStock> findByParameters(
        @Param("name") String name,
        @Param("size") String size,
        @Param("priceMin") Float priceMin,
        @Param("priceMax") Float priceMax,
        @Param("genericType") String genericType,
        @Param("specificType") String specificType,
        Pageable pageable
);

    
}
