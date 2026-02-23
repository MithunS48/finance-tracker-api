package com.project.finance_tracker_api.conf;

import com.project.finance_tracker_api.security.JwtFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.SecurityFilterChain;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityFilterConfig {

    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

       http.csrf(csrf->csrf.disable())
               .formLogin(form -> form.disable())
               .httpBasic(basic -> basic.disable())
               .authorizeHttpRequests(auth->auth
                        .requestMatchers("/api/login","/api/create").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/user/**").hasAnyRole("USER","ADMIN")
                )


               .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

       return http.build();
    }
}
