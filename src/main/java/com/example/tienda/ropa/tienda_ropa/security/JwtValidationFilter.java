package com.example.tienda.ropa.tienda_ropa.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.example.tienda.ropa.tienda_ropa.security.TokenJwtConfig.HEADER_AUTORIZATION;
import static com.example.tienda.ropa.tienda_ropa.security.TokenJwtConfig.PREFIX_TOKEN;
import static com.example.tienda.ropa.tienda_ropa.security.TokenJwtConfig.SECRET_KEY;
import static com.example.tienda.ropa.tienda_ropa.security.TokenJwtConfig.PERMIT_ALL_PATHS;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidationFilter extends BasicAuthenticationFilter{
    
    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
                
            String header = request.getHeader(HEADER_AUTORIZATION); //Primero recuperamos el header, del token del cliente o postman
            String path = request.getRequestURI();
             

            AntPathMatcher pathMatcher = new AntPathMatcher();
            boolean isPermitted = TokenJwtConfig.PERMIT_ALL_PATHS.stream()
                          .anyMatch(permittedPath -> pathMatcher.match(permittedPath, path));

        if (isPermitted) {
             System.out.println("entre aca !!");
            chain.doFilter(request, response);
            return;
         }

            String token = header.replace(PREFIX_TOKEN, ""); //Le quitamos el prefix tooken para dejarlo limpio

            try{
                Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
                String username = claims.getSubject();
                Object authoritiesClaims = claims.get("authorities");

                Collection<? extends GrantedAuthority> authorities =Arrays.asList(new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthoritiesJsonCreator.class).readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class));
                
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null, authorities);//el password se valida solo cuando creamos el token
                SecurityContextHolder.getContext().setAuthentication(authenticationToken); //Se autentica
                chain.doFilter(request, response);
            }catch(JwtException e){
               Map<String, String> body = new HashMap<>();
               body.put("error", e.getMessage());
               body.put("message", "El token JWT no es valido");

               response.getWriter().write(new ObjectMapper().writeValueAsString(body));
               response.setStatus(401);
               response.setContentType("aplication/json");

            }
           
    } 
   
}
