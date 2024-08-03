package com.example.tienda.ropa.tienda_ropa.repositories;

import com.example.tienda.ropa.tienda_ropa.entities.Comment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ICommentRepository extends CrudRepository<Comment,Long> {

    @SuppressWarnings("null")
    Optional<Comment> findById(Long id);
 
    Set<Comment> findByClotheId(String id);
}
