package com.project.finance_tracker_api.repository;

import com.project.finance_tracker_api.entity.RefreshToken;

import com.project.finance_tracker_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Integer> {


    Optional<RefreshToken> findByToken(String token);


    Optional<RefreshToken> findByUser(User user);

    void deleteByUser(User user);
}
