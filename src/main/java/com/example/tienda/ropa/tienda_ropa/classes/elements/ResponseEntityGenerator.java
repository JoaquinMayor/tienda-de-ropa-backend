package com.example.tienda.ropa.tienda_ropa.classes.elements;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseEntityGenerator {

    public static ResponseEntity<?> genetateResponseEntity(String message, int status, Object body){
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("status", status);
        if(body!=null){
            response.put("body", body);
        }

        return ResponseEntity.status(status).body(response);
    }
}
