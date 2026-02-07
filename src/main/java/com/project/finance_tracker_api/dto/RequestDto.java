package com.project.finance_tracker_api.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;



@Data

public class RequestDto {

    @Size(min = 3,max = 20)
    private String name;

    @Email
    private String email;


    @Size(min = 6)

    private String password;
}
