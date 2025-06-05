package org.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    public Integer getAge() {
        return age;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public String getEmail() {
        return email;
    }



    public String getName() {
        return name;
    }



    public Long getId() {
        return id;
    }


}
