package com.example.tienda.ropa.tienda_ropa.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tienda.ropa.tienda_ropa.entities.user.RestorePasswordData;

@Repository
public interface IRestorePasswordDataRepository extends JpaRepository<RestorePasswordData, Integer>{

    boolean existsByUserEmailAndCode(String email, String code);

}
