package com.example.tienda.ropa.tienda_ropa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TiendaRopaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiendaRopaApplication.class, args);
	}

}
