package com.example.tienda.ropa.tienda_ropa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import com.example.tienda.ropa.tienda_ropa.entities.User;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, String>{

    @SuppressWarnings("null")
    List<User> findAll();
    @SuppressWarnings("null")
    Optional<User> findById(String id);
    
    Optional<User> findByEmail(String email);

    Page<User> findByLastname(String lastname, Pageable pageable);

}
