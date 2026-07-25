package com.project.finance_tracker_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDto {

    private String accessToken;
    private String refreshToken;

}
