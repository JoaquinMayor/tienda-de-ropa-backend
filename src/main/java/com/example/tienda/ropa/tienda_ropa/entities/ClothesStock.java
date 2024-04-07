package com.example.tienda.ropa.tienda_ropa.entities;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;



@Entity
@Table(name= "clothesStock", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class ClothesStock {
    

    @Id
    private String id;

    @NotEmpty
    private String code;

    @NotEmpty
    private String name;

    @NotEmpty
    private String size;
    
    @NotNull
    private Double price;

    private String image;
    
    @NotEmpty
    private String description;
    
    @NotEmpty
    @Column(name = "generic_type")
    private String genericType;
    
    @NotEmpty
    @Column(name = "specific_type")
    private String specificType;
    
    @NotNull
    private Integer stock;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date publication;
    
    public ClothesStock(String id, String name, String size, double price, String image, String description,
            String genericType, String especificType, Integer stock, String code) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.price = price;
        this.image = image;
        this.description = description;
        this.genericType = genericType;
        this.specificType = especificType;
        this.stock = stock;
        this.publication = new Date();
        this.code = code;
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



    public double getPrice() {
        return price;
    }



    public void setPrice(double price) {
        this.price = price;
    }



    public String getImage() {
        return image;
    }



    public void setImage(String image) {
        this.image = image;
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



    public void setSpecificType(String especificType) {
        this.specificType = especificType;
    }



    public Integer getStock() {
        return stock;
    }



    public void setStock(Integer stock) {
        this.stock = stock;
    }



    public String getId() {
        return id;
    }



    public Date getPublication() {
        return publication;
    }

    public String getCode(){
        return code;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((size == null) ? 0 : size.hashCode());
        long temp;
        temp = Double.doubleToLongBits(price);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((image == null) ? 0 : image.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((genericType == null) ? 0 : genericType.hashCode());
        result = prime * result + ((specificType == null) ? 0 : specificType.hashCode());
        result = prime * result + ((stock == null) ? 0 : stock.hashCode());
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
        ClothesStock other = (ClothesStock) obj;
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
        if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
            return false;
        if (image == null) {
            if (other.image != null)
                return false;
        } else if (!image.equals(other.image))
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
        if (stock == null) {
            if (other.stock != null)
                return false;
        } else if (!stock.equals(other.stock))
            return false;
        return true;
    }
    

    
}
