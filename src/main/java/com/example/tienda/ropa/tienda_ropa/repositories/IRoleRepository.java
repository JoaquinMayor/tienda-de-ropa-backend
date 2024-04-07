package com.example.tienda.ropa.tienda_ropa.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.tienda.ropa.tienda_ropa.entities.Role;

public interface IRoleRepository extends CrudRepository<Role, String>{
    
    Optional<Role> findByName(String name);
}
