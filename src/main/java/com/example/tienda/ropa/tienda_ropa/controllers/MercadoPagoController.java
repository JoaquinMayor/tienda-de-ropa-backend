package com.example.tienda.ropa.tienda_ropa.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tienda.ropa.tienda_ropa.classes.ItemMercadoPago;
import com.example.tienda.ropa.tienda_ropa.services.MercadoPagoService;
import com.mercadopago.resources.preference.Preference;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/mercado")
public class MercadoPagoController {

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @PostMapping
    public Preference createPreferences(@RequestBody Set<ItemMercadoPago> items, BindingResult result){
       
        return this.mercadoPagoService.mercadoToken(items);
    }
    
}
