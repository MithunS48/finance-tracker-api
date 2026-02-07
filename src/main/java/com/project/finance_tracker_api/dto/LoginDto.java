package com.project.finance_tracker_api.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginDto {

    @Email
    private String email;

    private String password;
}
