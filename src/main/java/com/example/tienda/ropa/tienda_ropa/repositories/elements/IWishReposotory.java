package com.example.tienda.ropa.tienda_ropa.repositories.elements;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.tienda.ropa.tienda_ropa.entities.elements.Wish;

@Repository
public interface IWishReposotory extends JpaRepository<Wish, String> {

    @Query("SELECT CASE WHEN COUNT(w) > 0 THEN true ELSE false END " +
           "FROM Wish w JOIN w.users u " +
           "WHERE w.id = :wishId AND u.id = :userId")
    boolean existsByWishIdAndUserId(@Param("wishId") String wishId, @Param("userId") String userId);
}
