package com.example.tienda.ropa.tienda_ropa.classes.enums;

public enum EmailEnum {

    SubjectRestorePassword("Restauración de contraseña de cuenta de Tienda de Ropa"),
    MessageRestorePassword("Usted a solicitado recuperar su contraseña a continuación se le brindará un código que deberá ingresar para poder hacer el cambio Código:");
    
    private final String value;

    EmailEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
