package org.example.user_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.core.Relation;

@Schema(description = "Коллекция пользователей")
@Relation(collectionRelation = "users")
public class UsersCollectionDto extends CollectionModel<UserResponseDto> {
    
    public UsersCollectionDto(Iterable<UserResponseDto> content) {
        super(content);
    }
} 