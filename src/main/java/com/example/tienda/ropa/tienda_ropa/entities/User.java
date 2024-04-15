package com.example.tienda.ropa.tienda_ropa.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.JoinColumn;

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



    public User() {
    }
    
    public User(String id, String name, String lastname, String email, String tel, String password,
            String image) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.tel = tel;
        this.password = password;
        this.image = image;
        this.taxs = new HashSet<>();
        this.roles = new HashSet<>();
    }

    

    public String getId() {
        return id;
    }



    public void setPassword(String password) {
        this.password = password;
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



    public void setTaxs(Tax tax) {
        this.taxs.add(tax);
    }



    public String getPassword() {
        return password;
    }



    public Set<Tax> getTaxs() {
        return taxs;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((tel == null) ? 0 : tel.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((image == null) ? 0 : image.hashCode());
        return result;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (lastname == null) {
            if (other.lastname != null)
                return false;
        } else if (!lastname.equals(other.lastname))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (tel == null) {
            if (other.tel != null)
                return false;
        } else if (!tel.equals(other.tel))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (image == null) {
            if (other.image != null)
                return false;
        } else if (!image.equals(other.image))
            return false;
        return true;
    }

    
}
