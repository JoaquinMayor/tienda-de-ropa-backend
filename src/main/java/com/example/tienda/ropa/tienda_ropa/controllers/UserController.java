package com.example.tienda.ropa.tienda_ropa.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tienda.ropa.tienda_ropa.Interface.IValidation;
import com.example.tienda.ropa.tienda_ropa.entities.User;
import com.example.tienda.ropa.tienda_ropa.entities.UserDto;
import com.example.tienda.ropa.tienda_ropa.services.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/users")
public class UserController implements IValidation {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> list() {
        if (!userService.findAll().isEmpty()) {
            return ResponseEntity.ok(userService.findAll());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{password}")
    public ResponseEntity<?> create(@PathVariable String password, @Valid @RequestBody UserDto user,
            BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        User userNew = userService.save(user, password);
        return ResponseEntity.status(HttpStatus.CREATED).body(userNew);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody UserDto user, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }

        return userService.update(user);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/updatePassword/{id}/{password}")
    public ResponseEntity<?> updatePassword(@PathVariable String id, @PathVariable String password) {
        return userService.updatePassword(id, password);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/lastname/{lastname}")
    public ResponseEntity<?> findByLastname(@PathVariable String lastname) {
        Set<User> users = userService.findByLastname(lastname);
        if (!users.isEmpty()) {
            return ResponseEntity.ok(users);
        }

        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Map<String, String>> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
