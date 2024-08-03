package com.example.tienda.ropa.tienda_ropa.controllers;

import com.example.tienda.ropa.tienda_ropa.Interface.IValidation;
import com.example.tienda.ropa.tienda_ropa.classes.CommentInfo;

import com.example.tienda.ropa.tienda_ropa.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/comments")
public class CommentController implements IValidation {

    @Autowired
    CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CommentInfo comment, BindingResult result){
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        return this.commentService.addComment(comment.getIdUser(), comment.getIdProduct(), comment.getComment());

    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return this.commentService.deleteComment(id);
    }

    @GetMapping("/find/{idClothe}")
    public ResponseEntity<?> findComments(@PathVariable String idClothe){
        return this.commentService.findCommentsByClothe(idClothe);
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
