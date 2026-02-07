package com.project.finance_tracker_api.entity;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column( unique = true)
    private String email;

    private String password;

    private String role;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @PrePersist
    public void onCreate(){
        this.createdAt=LocalDateTime.now();
        this.modifiedAt=LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        this.modifiedAt=LocalDateTime.now();
    }
}
