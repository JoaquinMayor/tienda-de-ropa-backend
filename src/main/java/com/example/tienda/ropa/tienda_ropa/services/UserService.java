package com.example.tienda.ropa.tienda_ropa.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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

   /*  @Autowired
    private PasswordEncoder passwordEncoder;*/

    @Transactional
    public User save(UserDto userDto, String password){
        Optional<Role> optionalRole = roleRepository.findByName("ROLE_USER");
       // String passwordEncoded = passwordEncoder.encode(password);
        User user = new User(userDto.getId(),password,userDto.getLastname(),userDto.getEmail(),userDto.getTel(),password, userDto.getImage());
        user.setRole(optionalRole.orElseThrow());
        
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Set<User> findAll(){
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(String id){
        return userRepository.findById(id);
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
        Optional<User> userOptional  = userRepository.findById(userDto.getId());

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
}
