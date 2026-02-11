package com.project.finance_tracker_api.controller;

import com.project.finance_tracker_api.dto.LoginDto;
import com.project.finance_tracker_api.dto.RequestDto;
import com.project.finance_tracker_api.dto.ResponseDto;
import com.project.finance_tracker_api.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.project.finance_tracker_api.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserCont {

    private final UserService userService;



    @PostMapping("/create")
    public ResponseDto CreateUser(@Valid @RequestBody RequestDto requestDto){

        return userService.createUser(requestDto);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto){

        return  userService.loginVerify(loginDto);
    }

    @GetMapping("/all")
    public List<ResponseDto> getAllUser(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseDto getUserById(@PathVariable Integer id){
        return userService.getUserById(id);
    }
}
