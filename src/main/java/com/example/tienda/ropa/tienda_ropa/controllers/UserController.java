package com.example.tienda.ropa.tienda_ropa.controllers;

import java.util.HashMap;
import java.util.Map;

import com.example.tienda.ropa.tienda_ropa.entities.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.tienda.ropa.tienda_ropa.Interface.IValidation;
import com.example.tienda.ropa.tienda_ropa.classes.PageData;
import com.example.tienda.ropa.tienda_ropa.classes.UserDto;
import com.example.tienda.ropa.tienda_ropa.services.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/users")
public class UserController implements IValidation {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> list(@RequestBody PageData page) {
        final Pageable pageable = PageRequest.of(page.getPage(), page.getCant(), Sort.by(Sort.Direction.ASC, "name"));
        return this.userService.findAll(pageable);
    }

    @PostMapping("/register/{password}")
    public ResponseEntity<?> create(@PathVariable String password, @Valid @RequestBody UserDto user,
            BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }

        return this.userService.save(user, password);
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
        return this.userService.findById(id);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        return this.userService.findByEmail(email);
    }

    @GetMapping("/lastname/{lastname}")
    public ResponseEntity<?> findByLastname(@PathVariable String lastname, @RequestBody PageData page) {
        final Pageable pageable = PageRequest.of(page.getPage(), page.getCant(), Sort.by(Sort.Direction.ASC, "name"));
        return this.userService.findByLastname(lastname, pageable);
    }
    //El id del wish tiene que ser el mismo que el de la prenda al cual pertenece
    @PutMapping("/wish/{id}")
    public ResponseEntity<?> addWish(@PathVariable String id, @Valid @RequestBody Wish wish, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        return this.userService.addWish(id, wish);

    }

    @DeleteMapping("/wish/delete/{idUser}/{idWish}")
    public ResponseEntity<?> deleteWish(@PathVariable String idUser, @PathVariable String idWish){
        return this.userService.deleteWish(idUser, idWish);
    }

    @GetMapping("/detectWish/{idUser}/{idWish}")
    public ResponseEntity<?> detectWish(@PathVariable String idUser, @PathVariable String idWish){
        return this.userService.detectWish(idUser, idWish);
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
