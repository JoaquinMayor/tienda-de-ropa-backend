package com.example.tienda.ropa.tienda_ropa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
        .info(new Info().title("Tienda de Ropa Backend")
        .version("1.0.1")
        .description("Desarrollo de los distintos endPoint y pruebas para integrarlo a tu tienda online")
        .termsOfService("Mis Terminos")
        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
