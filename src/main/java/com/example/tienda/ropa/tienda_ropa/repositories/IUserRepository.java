package com.example.tienda.ropa.tienda_ropa.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.example.tienda.ropa.tienda_ropa.entities.User;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends CrudRepository<User, String>{

    @SuppressWarnings("null")
    Set<User> findAll();

    @SuppressWarnings("null")
    Optional<User> findById(String id);
    
    Optional<User> findByEmail(String email);

    Set<User> findByLastname(String lastname);
}
