package com.example.tienda.ropa.tienda_ropa.services.elements;

import com.example.tienda.ropa.tienda_ropa.classes.elements.ResponseEntityGenerator;
import com.example.tienda.ropa.tienda_ropa.classes.user.UserCommentDTO;
import com.example.tienda.ropa.tienda_ropa.entities.clothe.ClotheStock;
import com.example.tienda.ropa.tienda_ropa.entities.elements.Comment;
import com.example.tienda.ropa.tienda_ropa.entities.user.User;
import com.example.tienda.ropa.tienda_ropa.repositories.clothe.IClothesStockRepository;
import com.example.tienda.ropa.tienda_ropa.repositories.elements.ICommentRepository;
import com.example.tienda.ropa.tienda_ropa.repositories.user.IUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    ICommentRepository commentRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IClothesStockRepository clotheRepository;

    @Transactional
    public ResponseEntity<?> addComment(String idUser, String idClothes, String text){
        Optional<User> optionalUser = userRepository.findById(idUser);
        Optional<ClotheStock> optionalClothe = clotheRepository.findById(idClothes);

        if(optionalUser.isPresent()){
            if(optionalClothe.isPresent()){
                User user = optionalUser.orElseThrow();
                ClotheStock clothe = optionalClothe.orElseThrow();
                this.clotheRepository.save(clothe);
                return associateComment(text, user, clothe);
            }else{
                return ResponseEntityGenerator.genetateResponseEntity("Prenda no encontrada",404,null);
            }
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("Usuario no encontrado",404,null);
        }

    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findCommentsByClothe(String idClothe, Pageable pageable){
        Page<Comment> comments = commentRepository.findByClotheId(idClothe, pageable);
        List<UserCommentDTO> userComments = new ArrayList<>();
        for (Comment comment : comments) {
            if(comment.getAvailable()){
                Optional<User> userOptional = userRepository.findById(comment.getUser().getId());
                if(userOptional.isPresent()){
                    User user = userOptional.orElseThrow();
                    String fullName = user.getName() + " "+ user.getLastname();
                    UserCommentDTO userComment = new UserCommentDTO(fullName, user.getImage(), comment.getText());
                    userComments.add(userComment);
                }
            }
            
        }

        if(!comments.isEmpty()){
            return ResponseEntityGenerator.genetateResponseEntity("Commentarios encontrados con éxito", 200, userComments);
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

    private ResponseEntity<?> associateComment(String text, User user, ClotheStock clothe){   
                Comment comment = new Comment(text);
                comment.setClothe(clothe);
                comment.setUser(user);
                commentRepository.save(comment);
                user.setComment(comment);
                clothe.setComment(comment);
                userRepository.save(user);
                this.clotheRepository.save(clothe);
                return ResponseEntityGenerator.genetateResponseEntity("Comentario generado con éxito",201,comment);
    }
}
