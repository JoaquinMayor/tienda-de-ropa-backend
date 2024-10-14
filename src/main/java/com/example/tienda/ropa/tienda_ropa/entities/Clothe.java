package com.example.tienda.ropa.tienda_ropa.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@MappedSuperclass
public class Clothe {
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


    @NotEmpty
    private String description;

    @NotEmpty
    @Column(name = "generic_type")
    private String genericType;

    @NotEmpty
    @Column(name = "specific_type")
    private String specificType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date publication;


    
    public Clothe() {
    }

    public Clothe(String id, String code, String name, String size,
                  Double price, String description, String genericType,
                  String specificType, Date publication) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.size = size;
        this.price = price;
        this.description = description;
        this.genericType = genericType;
        this.specificType = specificType;
        this.publication = publication;
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

    public String getId() {
        return id;
    }

    public Date getPublication() {
        return publication;
    }

    public String getCode() {
        return code;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((size == null) ? 0 : size.hashCode());
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((genericType == null) ? 0 : genericType.hashCode());
        result = prime * result + ((specificType == null) ? 0 : specificType.hashCode());
        result = prime * result + ((publication == null) ? 0 : publication.hashCode());
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
        Clothe other = (Clothe) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
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
        if (publication == null) {
            if (other.publication != null)
                return false;
        } else if (!publication.equals(other.publication))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Clothes [id=" + id + ", code=" + code + ", name=" + name + ", size=" + size + ", price=" + price
                + ", description=" + description + ", genericType=" + genericType
                + ", specificType=" + specificType + ", publication=" + publication + "]";
    }

}
