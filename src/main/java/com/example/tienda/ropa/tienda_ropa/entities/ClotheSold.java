package com.example.tienda.ropa.tienda_ropa.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "clothesSold")
public class ClotheSold extends Clothe {

    @NotNull
    private Integer cant;

    @ManyToMany(mappedBy = "clothes")
    @JsonIgnore
    private Set<Tax> taxes;
    
    public ClotheSold() {
        this.taxes = new HashSet<>();
    }

    public ClotheSold(String id, String code, String name, String size,
                      Double price, String description, String genericType,
                      String specificType, Date publication, Integer cant) {
        super(id, code, name, size, price, description, genericType, specificType, publication);
        this.cant = cant;
        this.taxes = new HashSet<>();
    }

    public Integer getCant() {
        return cant;
    }

    public void setCant(Integer cant) {
        this.cant = cant;
    }

    public Set<Tax> getTaxes() {
        return taxes;
    }

    public void setTax(Tax tax){
        this.taxes.add(tax);
    }

    public void setTaxes(Set<Tax> taxes) {
        this.taxes = taxes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((cant == null) ? 0 : cant.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ClotheSold other = (ClotheSold) obj;
        if (cant == null) {
            if (other.cant != null)
                return false;
        } else if (!cant.equals(other.cant))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + "ClothesSold [cant=" + cant + taxes+ "]";
    }

}
