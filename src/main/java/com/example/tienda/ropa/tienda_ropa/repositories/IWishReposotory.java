package com.example.tienda.ropa.tienda_ropa.repositories;

import com.example.tienda.ropa.tienda_ropa.entities.Wish;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWishReposotory extends CrudRepository<Wish, String> {

}
