package org.example.user_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Schema(description = "Ответ с данными пользователя")
@Relation(collectionRelation = "users", itemRelation = "user")
public class UserResponseDto extends RepresentationModel<UserResponseDto> {
    @Schema(description = "Уникальный идентификатор пользователя", example = "1")
    @JsonProperty("id")
    private Long id;
    
    @Schema(description = "Имя пользователя", example = "Users")
    @JsonProperty("name")
    private String name;
    
    @Schema(description = "Email пользователя", example = "mail@mail.ru")
    @JsonProperty("email")
    private String email;
    
    @Schema(description = "Возраст пользователя", example = "25")
    @JsonProperty("age")
    private Integer age;

    @Schema(description = "Дата и время создания записи", example = "2024-01-15T10:30:00")
    @JsonProperty("createdat")
    private LocalDateTime createdat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreatedat() {
        return createdat;
    }

    public void setCreatedat(LocalDateTime createdat) {
        this.createdat = createdat;
    }

    @Override
    public String toString() {
        return "UserResponseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", createdAt=" + createdat +
                '}';
    }
}
