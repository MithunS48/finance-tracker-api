package com.project.finance_tracker_api.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseDto {

    private Integer id;
    private String name;

    private String email;

    private String role;

    private LocalDateTime createdAt;


}
