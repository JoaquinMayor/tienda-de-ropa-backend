package com.example.tienda.ropa.tienda_ropa.classes.user;

public class RestorePasswordDataDTO {

    private String email;

    private String code;
    
    private String password;

    public RestorePasswordDataDTO() {
    }

    public RestorePasswordDataDTO(String email, String code, String password) {
        this.email = email;
        this.code = code;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
