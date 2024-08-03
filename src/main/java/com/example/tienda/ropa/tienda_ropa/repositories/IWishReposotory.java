package com.example.tienda.ropa.tienda_ropa.repositories;

import com.example.tienda.ropa.tienda_ropa.entities.Wish;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IWishReposotory extends CrudRepository<Wish, String> {

    @Query("SELECT CASE WHEN COUNT(w) > 0 THEN true ELSE false END " +
           "FROM Wish w JOIN w.users u " +
           "WHERE w.id = :wishId AND u.id = :userId")
    boolean existsByWishIdAndUserId(@Param("wishId") String wishId, @Param("userId") String userId);
}
