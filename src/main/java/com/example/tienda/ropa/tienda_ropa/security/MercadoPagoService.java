package com.example.tienda.ropa.tienda_ropa.security;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.tienda.ropa.tienda_ropa.classes.ItemMercadoPago;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;

public class MercadoPagoService {
    
    public Preference mercadoToken(Set<ItemMercadoPago> items){
        MercadoPagoConfig.setAccessToken("PROD_ACCESS_TOKEN");
        List<PreferenceItemRequest> itemsRequest = new ArrayList<>();

        for(ItemMercadoPago item:items){
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .title(item.getTitle())
                    .description(item.getDescription())
                    .categoryId("clothe")
                    .quantity(item.getQuanty())
                    .currencyId(item.getCurrencyId())
                    .unitPrice(new BigDecimal(item.getUnitPrice()))
                    .build();
            itemsRequest.add(itemRequest);
        }

        PreferenceRequest preferenceRequest = PreferenceRequest.builder().items(itemsRequest).build();
        PreferenceClient client = new PreferenceClient();
        Preference preference = new Preference();
        try {
            preference = client.create(preferenceRequest);
        } catch (MPException e) {
            e.printStackTrace();
        } catch (MPApiException e) {
            e.printStackTrace();
        }

        return preference;
        
    }
}
