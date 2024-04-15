package com.example.tienda.ropa.tienda_ropa.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "clothesStock", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class ClothesStock extends Clothes {

    @NotNull
    private Integer stock;

    
    public ClothesStock() {
    }

    public ClothesStock(String id, String code, String name, String size,
            Double price, String image, String description, String genericType,
            String specificType, Date publication, Integer stock) {
        super(id, code, name, size, price, image, description, genericType, specificType, publication);
        this.stock = stock;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

}
