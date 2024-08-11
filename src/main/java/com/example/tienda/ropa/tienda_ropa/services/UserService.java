package com.example.tienda.ropa.tienda_ropa.services;

import java.util.Optional;
import java.util.Set;

import com.example.tienda.ropa.tienda_ropa.classes.ResponseEntityGenerator;
import com.example.tienda.ropa.tienda_ropa.entities.Wish;
import com.example.tienda.ropa.tienda_ropa.repositories.IWishReposotory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.tienda.ropa.tienda_ropa.entities.Role;
import com.example.tienda.ropa.tienda_ropa.entities.User;
import com.example.tienda.ropa.tienda_ropa.classes.UserDto;
import com.example.tienda.ropa.tienda_ropa.repositories.IRoleRepository;
import com.example.tienda.ropa.tienda_ropa.repositories.IUserRepository;

@Service
public class UserService {
    
    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IWishReposotory wishRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<?> save(UserDto userDto, String password){
        Optional<Role> optionalRole = roleRepository.findByName("ROLE_USER");
       
            String passwordEncoded = passwordEncoder.encode("password");
            User user = new User(userDto.getName(),userDto.getLastname(),userDto.getEmail(),userDto.getTel(),passwordEncoded, userDto.getImage(), userDto.getVip());
            user.setRole(optionalRole.orElseThrow());
    
            this.userRepository.save(user);
            return ResponseEntityGenerator.genetateResponseEntity("Usuario creado con éxito",201,user);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll(){
        Set<User> users = userRepository.findAll();
        return ResponseEntityGenerator.genetateResponseEntity("Usuarios encontrados con éxito",200,users);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findById(String id){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            return ResponseEntityGenerator.genetateResponseEntity("Usuario encontrado",200,userOptional.orElseThrow());
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("Usuario no encontrado",404,null);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findByEmail(String email){
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            return ResponseEntityGenerator.genetateResponseEntity("Usuario encontrado",200,userOptional.orElseThrow());
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("Usuario no encontrado",404,null);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findByLastname(String lastname){
        Set<User> users = userRepository.findByLastname(lastname);
        if(!users.isEmpty()){
            return ResponseEntityGenerator.genetateResponseEntity("Usuario encontrado",200,users);
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("Usuario no encontrado",404,null);
        }

    }

    @Transactional
    public ResponseEntity<?> update(UserDto userDto){
        Optional<User> userOptional  = userRepository.findById(userDto.getId());
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setEmail(userDto.getEmail());
            user.setImage(userDto.getImage());
            user.setLastname(userDto.getLastname());
            user.setName(userDto.getName());
            user.setTel(userDto.getTel());
            user.setVip(userDto.getVip());
            userRepository.save(user);
            return ResponseEntityGenerator.genetateResponseEntity("Usuario actualizado con éxito",201,user);
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("Usuario no encontrado",404,null);
        }

    }

    @Transactional
    public ResponseEntity<?> updatePassword(String id, String newPassword){
        Optional<User> userOptional  = userRepository.findById(id);

        if(userOptional.isPresent()){
            User user = userOptional.get();
            String passwordEncoded = passwordEncoder.encode(newPassword);
            user.setPassword(passwordEncoded);
            return ResponseEntityGenerator.genetateResponseEntity("Contraseña actualizada con éxito",200,user);
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("Usuario no encontrado",404,null);
        }

    }

    @Transactional
    public ResponseEntity<?> addWish(String idUser, Wish wish){
        Optional<User> userOptional = this.userRepository.findById(idUser);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setWish(wish);
            wish.setUser(user);
            wishRepository.save(wish);
            userRepository.save(user);
            return ResponseEntityGenerator.genetateResponseEntity("Deseo añadido con éxito",201,wish);
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("Usuario no encontrado",404,null);
        }
    }

    @Transactional
    public ResponseEntity<?> deleteWish(String idUser, String idWish){

        Optional<User> userOptional = this.userRepository.findById(idUser);
        Optional<Wish> wishOptional = this.wishRepository.findById(idWish);
        if(userOptional.isPresent() && wishOptional.isPresent()) {
            User user = userOptional.get();
            Wish wish = wishOptional.get();
            user.getWisheList().remove(wish);
            wish.getUsers().remove(user);
            userRepository.save(user);
            wishRepository.save(wish);

            return ResponseEntityGenerator.genetateResponseEntity("Deseo eliminado con éxito",200,wish);
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("Deseo no encontrado",404,null);
        }
    }


    @Transactional
    public ResponseEntity<?> detectWish(String idUser, String idWish){
        boolean existWish = this.wishRepository.existsByWishIdAndUserId(idWish, idUser);

        if(existWish){
            return ResponseEntityGenerator.genetateResponseEntity("Deseo Existente", 200, existWish);
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("Esta prenda no tiene deseo", 200, existWish);
        }
    }



}