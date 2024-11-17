package com.example.tienda.ropa.tienda_ropa.repositories.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tienda.ropa.tienda_ropa.entities.user.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, String>{
    
    Optional<Role> findByName(String name);
}
