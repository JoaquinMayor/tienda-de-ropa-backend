package com.example.tienda.ropa.tienda_ropa.services;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.tienda.ropa.tienda_ropa.classes.ItemMercadoPago;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;

@Service
public class MercadoPagoService {
    
    public Preference mercadoToken(Set<ItemMercadoPago> items){
        MercadoPagoConfig.setAccessToken("APP_USR-1711903969065227-092816-eba9ef1f5ef3e6acd0741eb1834d5618-2012239412");
        List<PreferenceItemRequest> itemsRequest = new ArrayList<>();

        for(ItemMercadoPago item:items){
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .id(item.getID())
                    .title(item.getTitle())
                    .description(item.getDescription())
                    .pictureUrl(item.getPicture())
                    .categoryId("ROPA")
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
            var apiResponse = e.getApiResponse();
            var content = apiResponse.getContent();
            System.out.println(content);
        }

        return preference;
        
    }
}
