package com.example.tienda.ropa.tienda_ropa.entities.clothe;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.tienda.ropa.tienda_ropa.entities.elements.Comment;
import com.example.tienda.ropa.tienda_ropa.entities.elements.Image;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "clothe")
    private Set<Comment> comments;

    
    public ClotheStock() {
    }

    public ClotheStock(String id, String code, String name, String size,
                       Double price, String description, String genericType,
                       String specificType, Date publication, Integer stock) {
        super(id, code, name, size, price, description, genericType, specificType, publication);
        this.stock = stock;
        this.images = new HashSet<>();
        this.comments = new HashSet<>();
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

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void setComment(Comment comment){
        this.comments.add(comment);
    }


}
