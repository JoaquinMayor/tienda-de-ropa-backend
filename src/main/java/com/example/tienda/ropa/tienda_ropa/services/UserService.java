package com.example.tienda.ropa.tienda_ropa.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.tienda.ropa.tienda_ropa.entities.Role;
import com.example.tienda.ropa.tienda_ropa.entities.User;
import com.example.tienda.ropa.tienda_ropa.entities.UserDto;
import com.example.tienda.ropa.tienda_ropa.repositories.IRoleRepository;
import com.example.tienda.ropa.tienda_ropa.repositories.IUserRepository;

import io.micrometer.core.ipc.http.HttpSender.Response;

@Service
public class UserService {
    
    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User save(UserDto userDto, String id, String password){
        Optional<Role> optionalRole = roleRepository.findByName("ROLE_USER");
        String passwordEncoded = passwordEncoder.encode(password);
        User user = new User(id, password,userDto.getLastname(),userDto.getEmail(),userDto.getTel(),passwordEncoded, userDto.getImage());
        user.setRole(optionalRole.orElse(null));
        
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Set<User> findAll(){
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Set<User> findByLastname(String lastname){
        return userRepository.findByLastname(lastname);
    }

    @Transactional
    public ResponseEntity<?> update(UserDto userDto){
        Optional<User> userOptional  = userRepository.findByEmail(userDto.getEmail());

        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setEmail(userDto.getEmail());
            user.setImage(userDto.getImage());
            user.setLastname(userDto.getLastname());
            user.setName(userDto.getName());
            user.setTel(userDto.getTel());
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Transactional
    public ResponseEntity<?> updatePassword(String email, String newPassword){
        Optional<User> userOptional  = userRepository.findByEmail(email);

        if(userOptional.isPresent()){
            User user = userOptional.get();
            String passwordEncoded = passwordEncoder.encode(newPassword);
            user.setPassword(passwordEncoded);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
