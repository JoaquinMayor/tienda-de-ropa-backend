package com.example.tienda.ropa.tienda_ropa.services.elements;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.tienda.ropa.tienda_ropa.entities.user.User;
import com.example.tienda.ropa.tienda_ropa.repositories.user.IUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



//Servicio que maneja las validaciones y generacion de token.
@Service
public class JpaUserDEtailsService implements UserDetailsService{

    @Autowired
    private IUserRepository repository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional =repository.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException(String.format("Username %s no existe en el sistema!", email));
        }
        User user = userOptional.orElseThrow();
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role-> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                user.getEnabled(),true,true,true,authorities);
    }
}