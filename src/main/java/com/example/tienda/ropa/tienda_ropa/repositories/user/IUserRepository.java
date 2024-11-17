package com.example.tienda.ropa.tienda_ropa.repositories.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.tienda.ropa.tienda_ropa.entities.user.User;

@Repository
public interface IUserRepository extends JpaRepository<User, String>{

    @SuppressWarnings("null")
    List<User> findAll();
    @SuppressWarnings("null")
    Optional<User> findById(String id);
    
    Optional<User> findByEmail(String email);

    Page<User> findByLastname(String lastname, Pageable pageable);

    @Query("select c from User c where c.vip = true")
    List<User> findVips();

}
