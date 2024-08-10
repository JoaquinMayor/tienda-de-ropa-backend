package com.example.tienda.ropa.tienda_ropa.services;

import com.example.tienda.ropa.tienda_ropa.classes.ResponseCommentView;
import com.example.tienda.ropa.tienda_ropa.classes.ResponseEntityGenerator;
import com.example.tienda.ropa.tienda_ropa.entities.ClotheStock;
import com.example.tienda.ropa.tienda_ropa.entities.Comment;
import com.example.tienda.ropa.tienda_ropa.entities.User;
import com.example.tienda.ropa.tienda_ropa.repositories.IClothesStockRepository;
import com.example.tienda.ropa.tienda_ropa.repositories.ICommentRepository;
import com.example.tienda.ropa.tienda_ropa.repositories.IUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CommentService {

    @Autowired
    ICommentRepository commentRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IClothesStockRepository clotheRepository;

    @Transactional
    public ResponseEntity<?> addComment(Long idUser, String idClothes, String text){
        Optional<User> optionalUser = userRepository.findById(idUser);
        Optional<ClotheStock> optionalClothe = clotheRepository.findById(idClothes);

        if(optionalUser.isPresent()){
            if(optionalClothe.isPresent()){
                User user = optionalUser.orElseThrow();
                ClotheStock clothe = optionalClothe.orElseThrow();
                Comment comment = new Comment(text);
                comment.setClothe(clothe);
                comment.setUser(user);
                commentRepository.save(comment);
                user.setComment(comment);
                clothe.setComment(comment);
                userRepository.save(user);
                this.clotheRepository.save(clothe);
                return ResponseEntityGenerator.genetateResponseEntity("Comentario generado con éxito",201,comment);
            }else{
                return ResponseEntityGenerator.genetateResponseEntity("Prenda no encontrada",404,null);
            }
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("Usuario no encontrado",404,null);
        }

    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findCommentsByClothe(String idClothe){
        Set<Comment> comments = commentRepository.findByClotheId(idClothe);
        List<String> userNames = new ArrayList<>();
        for (Comment comment : comments) {
            Optional<User> userOptional = userRepository.findById(comment.getUser().getId());
            if(userOptional.isPresent()){
                User user = userOptional.orElseThrow();
                String fullName = user.getName() + " "+ user.getLastname();
                userNames.add(fullName);
            }
        }
        ResponseCommentView commentView = new ResponseCommentView();
        commentView.setComments(comments);
        commentView.setListUsersNames(userNames);

        if(!comments.isEmpty()){
            return ResponseEntityGenerator.genetateResponseEntity("Commentarios encontrados con éxito", 200, commentView);
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("Comentarios no encontrados", 404, null);
        }

    }

    @Transactional
    public ResponseEntity<?> deleteComment(Long idComment){
        Optional<Comment> optionalComment = commentRepository.findById(idComment);
        if(optionalComment.isPresent()){
            Comment comment = optionalComment.orElseThrow();
            comment.setAvailable(false);
            commentRepository.save(comment);
            return ResponseEntityGenerator.genetateResponseEntity("Comentario eliminado con éxito",200,comment);
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("Comentario no encontrado",404,null);
        }

    }
}
