package com.example.tienda.ropa.tienda_ropa.entities.elements;

import com.example.tienda.ropa.tienda_ropa.entities.clothe.ClotheStock;
import com.example.tienda.ropa.tienda_ropa.entities.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String text;

    @NotNull
    private Boolean available;

    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_clothe")
    @JsonIgnore
    private ClotheStock clothe;

    public Comment(String text) {
        this.text = text;
        this.available = true;
    }

    public Comment() {
        this.available = true;
    }

    public ClotheStock getClothe() {
        return clothe;
    }

    public void setClothe(ClotheStock clothe) {
        this.clothe = clothe;
    }

    public  String getText() {
        return text;
    }

    public void setText( String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable( Boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    
}
