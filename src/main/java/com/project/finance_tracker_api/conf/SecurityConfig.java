package com.project.finance_tracker_api.conf;

import com.project.finance_tracker_api.entity.User;
import com.project.finance_tracker_api.repository.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner iniUser(UserRepo userRepo, PasswordEncoder passwordEncoder){
        return args -> {
            if(userRepo.count() ==0) {
                for (int i = 1; i <= 10; i++){
                    User user = new User();
                    user.setName("User" + i);
                    user.setEmail("user" + i + "@gmail.com");
                    user.setPassword(passwordEncoder.encode("pass" + i));
                    user.setRole("USER");
                    user.setCreatedAt(LocalDateTime.now());

                    userRepo.save(user);
                }
            }
        };
    }
}
