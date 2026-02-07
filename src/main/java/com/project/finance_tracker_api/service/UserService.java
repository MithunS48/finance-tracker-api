package com.project.finance_tracker_api.service;

import com.project.finance_tracker_api.security.JwtUnit;

import com.project.finance_tracker_api.dto.LoginDto;
import com.project.finance_tracker_api.dto.RequestDto;
import com.project.finance_tracker_api.dto.ResponseDto;
import com.project.finance_tracker_api.entity.User;
import com.project.finance_tracker_api.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {



    private final UserRepo userrepo;

    private final PasswordEncoder passwordEncoder;
    private final JwtUnit jwtUnit;

    public ResponseDto createUser(RequestDto requestDto){



        //convert DTO to Entity

        User user=new User();
        user.setName(requestDto.getName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRole("USER");

        User saveUser=userrepo.save(user);


        //entity to response dto

        ResponseDto response=new ResponseDto();
        response.setId(saveUser.getId());
        response.setName(saveUser.getName());
        response.setEmail(saveUser.getEmail());
        response.setRole(saveUser.getRole());
        response.setCreatedAt(saveUser.getCreatedAt());


        return response;
    }


    public String loginVerify(LoginDto loginDto) {

        User user=userrepo.findByEmail(loginDto.getEmail());

        if(user==null){
            return "user not found";
        }

        boolean isMatch=passwordEncoder.matches(
                loginDto.getPassword(),
                user.getPassword()
        );

        if(isMatch) {
            return jwtUnit.generateToken(user.getEmail());
        }
        else {
            return "Invalid credentials";
        }


    }
}
