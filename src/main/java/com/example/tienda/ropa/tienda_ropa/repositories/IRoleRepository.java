package com.example.tienda.ropa.tienda_ropa.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tienda.ropa.tienda_ropa.entities.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, String>{
    
    Optional<Role> findByName(String name);
}
