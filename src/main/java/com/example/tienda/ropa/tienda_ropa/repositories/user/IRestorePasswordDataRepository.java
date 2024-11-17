package com.example.tienda.ropa.tienda_ropa.repositories.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.tienda.ropa.tienda_ropa.entities.user.RestorePasswordData;

@Repository
public interface IRestorePasswordDataRepository extends JpaRepository<RestorePasswordData, Integer>{

     @Query("SELECT rpd FROM RestorePasswordData rpd WHERE rpd.code = :code AND rpd.user.email = :email")
    Optional<RestorePasswordData> findByCodeAndUserEmail(@Param("code") String code, @Param("email") String email);

}
