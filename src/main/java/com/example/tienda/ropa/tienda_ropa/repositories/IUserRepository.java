package com.example.tienda.ropa.tienda_ropa.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.example.tienda.ropa.tienda_ropa.entities.User;

public interface IUserRepository extends CrudRepository<User, String>{

    Set<User> findAll();
    
    Optional<User> findByEmail(String email);

    Set<User> findByLastname(String lastname);
}
