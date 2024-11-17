package com.example.tienda.ropa.tienda_ropa.classes.user;

public class UserCommentDTO {

    private String fullname;
    private String image;
    private String comment;
    public UserCommentDTO(String fullname, String image, String comment) {
        this.fullname = fullname;
        this.image = image;
        this.comment = comment;
    }
    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    
}
