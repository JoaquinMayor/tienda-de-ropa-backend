package com.example.tienda.ropa.tienda_ropa.repositories;

import com.example.tienda.ropa.tienda_ropa.entities.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICommentRepository extends CrudRepository<Comment,String> {

    Optional<Comment> findById(Long id);
}
