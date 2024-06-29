package com.example.tienda.ropa.tienda_ropa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "wishes")
public class Wish {

    @Id
    private String id;
    @NotEmpty
    private String url;
    @NotEmpty
    private String name;
    @NotEmpty
    private String photo;

    @ManyToMany(mappedBy = "wisheList")
    @JsonIgnoreProperties({"wishes","handler","hibernateLazyInitializer"})
    @JsonIgnore
    private Set<User> users;

    public Wish(String id, String link, String photo, String name) {
        this.id = id;
        this.url = link;
        this.photo = photo;
        this.name = name;
        this.users = new HashSet<>();
    }

    public Wish() {
        this.users = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String link) {
        this.url = link;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
    public void setUser(User user){
        this.users.add(user);
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wish wish = (Wish) o;
        return Objects.equals(id, wish.id) && Objects.equals(url, wish.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url);
    }
}
