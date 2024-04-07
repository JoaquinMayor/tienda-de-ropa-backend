package com.example.tienda.ropa.tienda_ropa.entities;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tax")
public class Tax {
    @Id
    private String id;

    @NotEmpty
    private String price;

    @OneToMany()
    @JoinColumn(name = "tax_id")
    private List<Clothes> clothes;

    @NonNull
    private Date date;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @NotNull
    private Double travelCost;

    @NotEmpty
    private String adress;

    public Tax(String id, String price, Date date, User user, Double travelCost, String adress) {
        this.id = id;
        this.price = price;
        this.date = date;
        this.user = user;
        this.travelCost = travelCost;
        this.adress = adress;
        this.clothes = new ArrayList<>();
    }

    

    public String getId() {
        return id;
    }



    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
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

    public Double getTravelCost() {
        return travelCost;
    }

    public void setTravelCost(Double travelCost) {
        this.travelCost = travelCost;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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

    

}
