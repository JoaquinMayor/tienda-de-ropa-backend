package com.example.tienda.ropa.tienda_ropa.classes;

public class ItemMercadoPago {

    private String id;
    private String title;
    private Integer quanty;
    private Double unitPrice;
    private String currencyId;
    private String description;

    public ItemMercadoPago() {
    }


    public ItemMercadoPago(String id, String title, Integer quanty, Double unitPrice, String currencyId, String description, String picture) {
        this.title = title;
        this.quanty = quanty;
        this.unitPrice = unitPrice;
        this.currencyId = currencyId;
        this.description = description;
        this.id = id;
    }

    public String getID(){
        return this.id;
    }

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public Integer getQuanty() {
        return quanty;
    }


    public void setQuanty(Integer quanty) {
        this.quanty = quanty;
    }


    public Double getUnitPrice() {
        return unitPrice;
    }


    public void setUnitPrice(Double price) {
        this.unitPrice = price;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
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
        ItemMercadoPago other = (ItemMercadoPago) obj;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }


}
