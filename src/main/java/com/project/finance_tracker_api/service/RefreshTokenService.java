package com.project.finance_tracker_api.service;

import com.project.finance_tracker_api.entity.RefreshToken;
import com.project.finance_tracker_api.entity.User;
import com.project.finance_tracker_api.repository.RefreshTokenRepo;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenService {

     private final RefreshTokenRepo refreshTokenRepo;

     private static final long Duration_day=7;

     public RefreshToken refreshToken(User user){

         refreshTokenRepo.deleteByUser(user);

         RefreshToken refreshToken=new RefreshToken();

         refreshToken.setUser(user);
         refreshToken.setToken(UUID.randomUUID().toString());
         refreshToken.setExpiryDate(Instant.now().plus(Duration_day, ChronoUnit.DAYS));

         return refreshTokenRepo.save(refreshToken);

     }

     public RefreshToken verifyExpiration(RefreshToken token){

         if(token.getExpiryDate().isBefore(Instant.now())){
             refreshTokenRepo.delete(token);
             throw  new RuntimeException("Refresh token expired. Please login again.");
         }
         return token;
     }


}
