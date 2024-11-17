package com.example.tienda.ropa.tienda_ropa.entities.user;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.example.tienda.ropa.tienda_ropa.entities.elements.Comment;
import com.example.tienda.ropa.tienda_ropa.entities.elements.Tax;
import com.example.tienda.ropa.tienda_ropa.entities.elements.Wish;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User {
    
    @Id
    private String id;

    @NotEmpty
    private String name;
    
    @NotEmpty
    private String lastname;

    @NotEmpty
    private String email;

    @NotEmpty
    private String tel;

    @NotEmpty
    private String password;

    @NotEmpty
    private String image;

    @NotNull
    private Boolean enabled;

    @NotNull
    private boolean vip;

    @ManyToMany
    @JoinTable(
            name="whishesxusers",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_wish"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"id_user", "id_wish"})}
    )
    private Set<Wish> wisheList;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private Set<Comment> comments;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private Set<Tax> taxs;

    @JsonIgnoreProperties({"users","handler","hibernateLazyInitializer"})
    @ManyToMany
    @JoinTable(
        name = "rolesxusers",
        joinColumns = @JoinColumn(name = "id_user"),
        inverseJoinColumns = @JoinColumn(name = "id_rol"),
        uniqueConstraints = {@UniqueConstraint(columnNames = {"id_user", "id_rol"})}
    )
    private Set<Role> roles;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @JsonIgnore
    Set<RestorePasswordData> restoredPassword;



    public User() {
        this.taxs = new HashSet<>();
        this.roles = new HashSet<>();
        this.wisheList = new HashSet<>();
        this.comments = new HashSet<>();
        this.restoredPassword = new HashSet<>();
        this.enabled = true;
    }
    
    public User(String name, String lastname, String email, String tel, String password,
            String image, boolean vip) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.tel = tel;
        this.password = password;
        this.image = image;
        this.vip = vip;
        this.taxs = new HashSet<>();
        this.roles = new HashSet<>();
        this.wisheList = new HashSet<>();
        this.restoredPassword = new HashSet<>();
        this.enabled = true;
    }

    

    public String getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip( boolean vip) {
        this.vip = vip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public Set<Role> getRoles() {
        return roles;
    }


    public void setRole(Role role) {
        this.roles.add(role);
    }


    public void setTax(Tax tax) {
        this.taxs.add(tax);
    }


    public String getPassword() {
        return password;
    }


    public Set<Tax> getTaxs() {
        return taxs;
    }

    public void setTaxs(Set<Tax> taxs) {
        this.taxs = taxs;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Wish> getWisheList() {
        return wisheList;
    }

    public void setWisheList(Set<Wish> wisheList) {
        this.wisheList = wisheList;
    }

    public void setWish(Wish wish) {
        this.wisheList.add(wish);
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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

    public void setRestorePassword(RestorePasswordData restore){
        this.restoredPassword.add(restore);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }


    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}
