package com.example.tienda.ropa.tienda_ropa.classes.user;

//Editamos todo lo que queremos que se muestre en nuestro email
public class EmailDTO {

    private String addressee;
    private String subject;
    private String message;

    public EmailDTO() {
    }

    public EmailDTO(String addressee, String subject, String message) {
        this.addressee = addressee;
        this.subject = subject;
        this.message = message;
    }

    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
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
