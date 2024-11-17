package com.example.tienda.ropa.tienda_ropa.repositories.elements;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tienda.ropa.tienda_ropa.entities.elements.Comment;

import java.util.Optional;

@Repository
public interface ICommentRepository extends JpaRepository<Comment,Long> {

    @SuppressWarnings("null")
    Optional<Comment> findById(Long id);
 
    Page<Comment> findByClotheId(String id, Pageable pageable);
}
