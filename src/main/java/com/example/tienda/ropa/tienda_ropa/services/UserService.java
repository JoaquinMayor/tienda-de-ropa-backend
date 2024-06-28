package com.example.tienda.ropa.tienda_ropa.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.example.tienda.ropa.tienda_ropa.entities.Wish;
import com.example.tienda.ropa.tienda_ropa.repositories.IWishReposotory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.tienda.ropa.tienda_ropa.entities.Role;
import com.example.tienda.ropa.tienda_ropa.entities.User;
import com.example.tienda.ropa.tienda_ropa.entities.UserDto;
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
    private Repositories repositories;

   /*  @Autowired
    private PasswordEncoder passwordEncoder;*/

    @Transactional
    public ResponseEntity<?> save(UserDto userDto, String password){
        Map<String, Object> response = new HashMap<>();
        Optional<Role> optionalRole = roleRepository.findByName("ROLE_USER");
       // String passwordEncoded = passwordEncoder.encode(password);
        User user = new User(userDto.getId(),userDto.getName(),userDto.getLastname(),userDto.getEmail(),userDto.getTel(),password, userDto.getImage());
        user.setRole(optionalRole.orElseThrow());
        response.put("user", user);
        response.put("messege", "Usuario creado con éxito");
        response.put("status", 201);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll(){
        Map<String, Object> response = new HashMap<>();
        Set<User> users = userRepository.findAll();
        response.put("users", users);
        response.put("messege", "Usuarios encontrados con éxito");
        response.put("status", 200);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findById(String id){
        Map<String, Object> response = new HashMap<>();
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            response.put("user", userOptional.get());
            response.put("messege", "Usuario encontrado");
            response.put("status", 200);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            response.put("messege", "Usuario no encontrado");
            response.put("status", 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findByEmail(String email){
        Map<String, Object> response = new HashMap<>();
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            response.put("user", userOptional.get());
            response.put("messege", "Usuario encontrado");
            response.put("status", 200);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            response.put("messege", "Usuario no encontrado");
            response.put("status", 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findByLastname(String lastname){
        Map<String, Object> response = new HashMap<>();
        Set<User> users = userRepository.findByLastname(lastname);
        if(!users.isEmpty()){
            response.put("user", users);
            response.put("messege", "Usuario encontrado");
            response.put("status", 200);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            response.put("messege", "Usuario no encontrado");
            response.put("status", 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

    @Transactional
    public ResponseEntity<?> update(UserDto userDto){
        Optional<User> userOptional  = userRepository.findById(userDto.getId());
        Map<String, Object> response = new HashMap<>();
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setEmail(userDto.getEmail());
            user.setImage(userDto.getImage());
            user.setLastname(userDto.getLastname());
            user.setName(userDto.getName());
            user.setTel(userDto.getTel());
            userRepository.save(user);
            response.put("user", user);
            response.put("status", 201);
            response.put("message", "Usuario actualizado con éxito");
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }else{
            response.put("status", 404);
            response.put("message", "Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

    @Transactional
    public ResponseEntity<?> updatePassword(String id, String newPassword){
        Optional<User> userOptional  = userRepository.findById(id);

        if(userOptional.isPresent()){
            User user = userOptional.get();
            //String passwordEncoded = passwordEncoder.encode(newPassword);
            user.setPassword(newPassword);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Transactional
    public ResponseEntity<?> addWish(String idUser, Wish wish){
        Map<String, Object> response = new HashMap<>();
        Optional<User> userOptional = this.userRepository.findById(idUser);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setWish(wish);
            wish.setUser(user);
            userRepository.save(user);
            wishRepository.save(wish);
            response.put("status", 201);
            response.put("message", "Deseo añadido con éxito");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        }else{
            response.put("status", 404);
            response.put("message", "Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @Transactional
    public ResponseEntity<?> deleteWish(String idUser, String idWish){
        Map<String, Object> response = new HashMap<>();
        Optional<User> userOptional = this.userRepository.findById(idUser);
        Optional<Wish> wishOptional = this.wishRepository.findById(idWish);
        if(userOptional.isPresent() && wishOptional.isPresent()) {
            User user = userOptional.get();
            Wish wish = wishOptional.get();
            user.getWisheList().remove(wish);
            wish.getUsers().remove(user);
            userRepository.save(user);
            wishRepository.save(wish);

            response.put("user", user);
            response.put("status", 200);
            response.put("message", "Deseo eliminado con éxito");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            response.put("status", 404);
            response.put("message", "Usuario o deseo no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
