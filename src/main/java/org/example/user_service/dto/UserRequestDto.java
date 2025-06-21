package org.example.user_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Запрос на создание или обновление пользователя")
public class UserRequestDto  {

    @Schema(description = "Имя пользователя", example = "Users", required = true)
    @NotBlank(message = "Укажите имя")
    private String name;

    @Schema(description = "Email пользователя", example = "mail@mail.ru", required = true)
    @NotBlank(message = "Укажите email")
    @Email(message = "Email должен быть действительным")
    private String email;

    @Schema(description = "Возраст пользователя", example = "25", required = true, minimum = "0")
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