package com.example.tienda.ropa.tienda_ropa.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "clothesSold")
public class ClothesSold extends Clothes {

    @NotNull
    private Integer cant;

    @ManyToOne
    @JoinColumn(name = "id_tax")
    @JsonIgnore
    private Tax tax;
    
    public ClothesSold() {
    }

    public ClothesSold(String id, String code, String name, String size,
            Double price, String description, String genericType,
            String specificType, Date publication, Integer cant, Tax tax) {
        super(id, code, name, size, price, description, genericType, specificType, publication);
        this.cant = cant;
        this.tax = tax;
    }

    public Integer getCant() {
        return cant;
    }

    public void setCant(Integer cant) {
        this.cant = cant;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
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
        ClothesSold other = (ClothesSold) obj;
        if (cant == null) {
            if (other.cant != null)
                return false;
        } else if (!cant.equals(other.cant))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + "ClothesSold [cant=" + cant + tax+ "]";
    }

}
