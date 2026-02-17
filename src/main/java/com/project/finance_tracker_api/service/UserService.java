package com.project.finance_tracker_api.service;

import com.project.finance_tracker_api.exception.UserNotFoundException;
import com.project.finance_tracker_api.security.JwtUnit;

import com.project.finance_tracker_api.dto.LoginDto;
import com.project.finance_tracker_api.dto.RequestDto;
import com.project.finance_tracker_api.dto.ResponseDto;
import com.project.finance_tracker_api.entity.User;
import com.project.finance_tracker_api.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {



    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;
    private final JwtUnit jwtUnit;

    public ResponseDto createUser(RequestDto requestDto){



        //convert DTO to Entity

        User user=new User();
        user.setName(requestDto.getName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRole("USER");

        User saveUser=userRepo.save(user);


        //entity to response dto

        ResponseDto response=new ResponseDto();
        response.setId(saveUser.getId());
        response.setName(saveUser.getName());
        response.setEmail(saveUser.getEmail());
        response.setRole(saveUser.getRole());
        response.setCreatedAt(saveUser.getCreatedAt());


        return response;
    }

    //Login verification
    public String loginVerify(LoginDto loginDto) {

        User user=userRepo.findByEmail(loginDto.getEmail());

        if(user==null){
            throw  new UserNotFoundException("user not found");
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


    public List<ResponseDto> getAllUsers(){

        List<User> users=userRepo.findAll();

        List<ResponseDto> responseDtoList=new ArrayList<>();

        for(User u:users){
            ResponseDto res=new ResponseDto();
            res.setId(u.getId());
            res.setName(u.getName());
            res.setEmail(u.getEmail());
            res.setCreatedAt(u.getCreatedAt());


            responseDtoList.add(res);

        }

        return responseDtoList;
    }

    public ResponseDto getUserById(Integer id){
        User user=userRepo.findById(id).orElse(null);

        if(user==null){
            throw  new UserNotFoundException("user not found");
        }
        ResponseDto res=new ResponseDto();
        res.setId(user.getId());
        res.setName(user.getName());
        res.setEmail(user.getEmail());
        res.setCreatedAt(user.getCreatedAt());

        return res;
    }

    public ResponseDto updateUser(Integer id, RequestDto info) {

        User user=userRepo.findById(id).orElse(null);
        if(user==null){
            throw  new UserNotFoundException("user not found");
        }

        user.setName(info.getName());
        user.setEmail(info.getEmail());
        userRepo.save(user);

        ResponseDto res=new ResponseDto();
        res.setId(user.getId());
        res.setName(user.getName());
        res.setEmail(user.getEmail());
        return res;
    }

    public String deleteUser(Integer id) {
        User user=userRepo.findById(id).orElse(null);
        if(user==null){
            throw  new UserNotFoundException("user not found");
        }
        userRepo.deleteById(id);
        return "User deleted "+id+" successfully";

    }
}
