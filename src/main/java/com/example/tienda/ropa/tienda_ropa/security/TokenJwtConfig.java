package com.example.tienda.ropa.tienda_ropa.security;

import java.util.Arrays;
import java.util.List;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;


public class TokenJwtConfig {
    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build(); //Creando llave de seguridad, la primera parte de la clave, solo se queda en el servidor no tiene que ir a ningun otro lado
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String HEADER_AUTORIZATION = "Authorization";

    public static final List<String> PERMIT_ALL_PATHS = Arrays.asList(
    "/api/users/register/**",
    "/api/users/update",
    "/api/clothes",
    "/api/clothes/code/**",
    "/api/clothes/find",
    "/login",
    "/api/mercado"
);
}
