package com.project.finance_tracker_api.security;

import com.project.finance_tracker_api.dto.ResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;


import java.util.Date;
import javax.crypto.SecretKey;

@Component
public class JwtUnit {

    private final SecretKey secret_key= Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(ResponseDto responseDto){
        return Jwts.builder()
                .setSubject(responseDto.getEmail())
                .claim("role",responseDto.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(secret_key)
                .compact();
    }
    public String extractEmail(String token){
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public String extractRole(String token){
        return extractClaims(token).get("role",String.class);
    }

    public boolean isValidToken(String token){
        try{
            extractClaims(token);
            return !isTokenExpired(token);
        }catch (Exception ex){
            return false;
        }

    }

    private Claims extractClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(secret_key)
                .build()
                .parseClaimsJws(token)
                .getBody();

    }


}
