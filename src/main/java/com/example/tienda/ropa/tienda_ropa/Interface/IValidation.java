package com.example.tienda.ropa.tienda_ropa.Interface;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface IValidation {

    ResponseEntity<Map<String, String>> validation(BindingResult result);
}
