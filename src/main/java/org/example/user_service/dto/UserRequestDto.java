package org.example.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;


public class UserRequestDto  {

    @NotBlank(message = "Укажите имя")
    private String name;

    @NotBlank(message = "Укажите email")
    @Email(message = "Email должен быть действительным")
    private String email;

    @NotNull(message = "Укажите возраст")
    @PositiveOrZero(message = "Возрост не может быть меньше 0")
    private Integer age;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}