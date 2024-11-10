package com.example.tienda.ropa.tienda_ropa.entities;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tax", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class Tax {
    @Id
    private String id;

    @NotEmpty
    @Column(name = "code")
    private String code;

    @NotNull
    private Float price;

    @NotNull
    @Column(name = "travel_cost")
    private Double travelCost;

    @NotEmpty
    private String adress;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "date_creation")
    @NonNull
    private Date date;

    @ManyToMany
    @JoinTable(
        name = "taxxclothesold",
        joinColumns= @JoinColumn(name = "tax_id"),
        inverseJoinColumns = @JoinColumn(name = "clothe_id"),
        uniqueConstraints = {@UniqueConstraint(columnNames = {"tax_id","clothe_id"})}
    )
    private Set<ClotheSold> clothes;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

  

    public Tax() {
        this.clothes = new HashSet<>();
    }

    public Tax(String id, Float price, Date date, User user, Double travelCost, String adress, String code) {
        this.id = id;
        this.price = price;
        this.date = date;
        this.user = user;
        this.travelCost = travelCost;
        this.adress = adress;
        this.clothes = new HashSet<>();
        this.code = code;
    }

    

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Set<ClotheSold> getClothes() {
        return clothes;
    }


    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setClothe(ClotheSold clothe){
        this.clothes.add(clothe);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getTravelCosto() {
        return travelCost;
    }

    public void setTravelCosto(Double travelCost) {
        this.travelCost = travelCost;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getTravelCost() {
        return travelCost;
    }

    public void setTravelCost(Double travelCost) {
        this.travelCost = travelCost;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((travelCost == null) ? 0 : travelCost.hashCode());
        result = prime * result + ((adress == null) ? 0 : adress.hashCode());
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
        Tax other = (Tax) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (price == null) {
            if (other.price != null)
                return false;
        } else if (!price.equals(other.price))
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (travelCost == null) {
            if (other.travelCost != null)
                return false;
        } else if (!travelCost.equals(other.travelCost))
            return false;
        if (adress == null) {
            if (other.adress != null)
                return false;
        } else if (!adress.equals(other.adress))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Tax [id=" + id + ", code=" + code + ", price=" + price + ", travelCost=" + travelCost + ", adress="
                + adress + ", date=" + date + ", user=" + user  +"]";
    }

    public void setClothes(Set<ClotheSold> clothes) {
        this.clothes = clothes;
    }

       @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

}
