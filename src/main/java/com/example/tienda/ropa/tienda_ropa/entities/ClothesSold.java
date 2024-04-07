package com.example.tienda.ropa.tienda_ropa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name= "clothesSold")
public class ClothesSold {
    
    @Id
    private String id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String size;
    
    @NotNull
    private Double price;
    
    @NotEmpty
    private String description;
    
    @NotEmpty
    @Column(name = "generic_type")
    private String genericType;
    
    @NotEmpty
    @Column(name = "specific_type")
    private String specificType;
    
    @NotNull
    private Integer cant;

    @ManyToOne
    @JoinColumn(name = "tax_id")
    @JsonIgnore
    @NotNull
    private Tax tax;

    public ClothesSold(String id, String name, String size, Double price,
         String description, String genericType, String specificType,
            Integer cant, Tax tax) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.price = price;
        this.description = description;
        this.genericType = genericType;
        this.specificType = specificType;
        this.cant = cant;
        this.tax = tax;
    }


    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((size == null) ? 0 : size.hashCode());
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((genericType == null) ? 0 : genericType.hashCode());
        result = prime * result + ((specificType == null) ? 0 : specificType.hashCode());
        result = prime * result + ((cant == null) ? 0 : cant.hashCode());
        return result;
    }



    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenericType() {
        return genericType;
    }

    public void setGenericType(String genericType) {
        this.genericType = genericType;
    }

    public String getSpecificType() {
        return specificType;
    }

    public void setSpecificType(String specificType) {
        this.specificType = specificType;
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



    public String getId() {
        return id;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ClothesSold other = (ClothesSold) obj;
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
        if (size == null) {
            if (other.size != null)
                return false;
        } else if (!size.equals(other.size))
            return false;
        if (price == null) {
            if (other.price != null)
                return false;
        } else if (!price.equals(other.price))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (genericType == null) {
            if (other.genericType != null)
                return false;
        } else if (!genericType.equals(other.genericType))
            return false;
        if (specificType == null) {
            if (other.specificType != null)
                return false;
        } else if (!specificType.equals(other.specificType))
            return false;
        if (cant == null) {
            if (other.cant != null)
                return false;
        } else if (!cant.equals(other.cant))
            return false;
        return true;
    }


    
}
