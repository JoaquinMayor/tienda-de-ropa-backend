package com.example.tienda.ropa.tienda_ropa.entities;


import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;



@Entity
@Table(name= "clothes")
public class Clothes {
    
    final static Integer stock = 0;

    @Id
    private String id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String size;
    
    @NotNull
    private Double price;

    @NotEmpty
    private String image;
    
    @NotEmpty
    private String description;
    
    @NotEmpty
    private String genericType;
    
    @NotEmpty
    private String especificType;
    
    @NotNull
    private Integer cant;
    
    @NotNull
    private Date publication;
    
    public Clothes(String id, String name, String size, double price, String image, String description,
            String genericType, String especificType, Integer cant) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.price = price;
        this.image = image;
        this.description = description;
        this.genericType = genericType;
        this.especificType = especificType;
        this.cant = cant;
        this.publication = new Date();;
    }


    
    public static Integer getStock() {
        return stock;
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



    public String getEspecificType() {
        return especificType;
    }



    public void setEspecificType(String especificType) {
        this.especificType = especificType;
    }



    public Integer getCant() {
        return cant;
    }



    public void setCant(Integer cant) {
        this.cant = cant;
    }



    public String getId() {
        return id;
    }



    public Date getPublication() {
        return publication;
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
        result = prime * result + ((especificType == null) ? 0 : especificType.hashCode());
        result = prime * result + ((cant == null) ? 0 : cant.hashCode());
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
        Clothes other = (Clothes) obj;
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
        if (especificType == null) {
            if (other.especificType != null)
                return false;
        } else if (!especificType.equals(other.especificType))
            return false;
        if (cant == null) {
            if (other.cant != null)
                return false;
        } else if (!cant.equals(other.cant))
            return false;
        return true;
    }
    

    
}
