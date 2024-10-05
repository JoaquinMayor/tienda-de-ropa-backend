package com.example.tienda.ropa.tienda_ropa.classes;

public class PageData {
    
    private Integer page;
    private Integer cant;
    
    public PageData() {
    }
    
    public PageData(Integer page, Integer cant) {
        this.page = page;
        this.cant = cant;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getCant() {
        return cant;
    }

    public void setCant(Integer cant) {
        this.cant = cant;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((page == null) ? 0 : page.hashCode());
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
        PageData other = (PageData) obj;
        if (page == null) {
            if (other.page != null)
                return false;
        } else if (!page.equals(other.page))
            return false;
        if (cant == null) {
            if (other.cant != null)
                return false;
        } else if (!cant.equals(other.cant))
            return false;
        return true;
    }

    
}
