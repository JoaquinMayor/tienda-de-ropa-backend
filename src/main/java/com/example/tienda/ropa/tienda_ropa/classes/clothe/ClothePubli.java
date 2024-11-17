package com.example.tienda.ropa.tienda_ropa.classes.clothe;

import com.example.tienda.ropa.tienda_ropa.entities.clothe.ClotheStock;

import jakarta.validation.constraints.NotNull;

public class ClothePubli {

    @NotNull
    private ClotheStock clothe;
    private String subject;
    private String message;

    public ClothePubli() {
    }

    public ClothePubli(ClotheStock clothe, String subject, String message) {
        this.clothe = clothe;
        this.subject = subject;
        this.message = message;
    }

    public ClotheStock getClothe() {
        return clothe;
    }

    public void setClothe(ClotheStock clothe) {
        this.clothe = clothe;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
