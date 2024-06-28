package com.example.tienda.ropa.tienda_ropa.entities;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "clothesStock", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class ClotheStock extends Clothe {

    @NotNull
    private Integer stock;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "clotheStock")
    private Set<Image> images;

    
    public ClotheStock() {
    }

    public ClotheStock(String id, String code, String name, String size,
                       Double price, String description, String genericType,
                       String specificType, Date publication, Integer stock) {
        super(id, code, name, size, price, description, genericType, specificType, publication);
        this.stock = stock;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    

}
