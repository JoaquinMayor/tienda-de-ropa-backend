package com.example.tienda.ropa.tienda_ropa.classes;

public class CommentInfo {

    private Long idUser;
    private String idProduct;
    private String comment;

    public CommentInfo() {
    }

    public CommentInfo(String comment, String idProduct, Long idUser) {
        this.comment = comment;
        this.idProduct = idProduct;
        this.idUser = idUser;
    }

    public String getComment() {
        return comment;
    }

    public Long getIdUser() {
        return idUser;
    }

    public String getIdProduct() {
        return idProduct;
    }
}
