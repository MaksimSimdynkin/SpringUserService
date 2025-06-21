package org.example.user_service.service;

import org.example.user_service.dto.UserResponseDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class HateoasService {

    
    public UserResponseDto addLinks(UserResponseDto user) {
        if (user == null) return null;
        
        user.removeLinks();
        
        user.add(linkTo(methodOn(org.example.user_service.controller.UserController.class)
                .getUserById(user.getId()))
                .withSelfRel());
        
        user.add(linkTo(methodOn(org.example.user_service.controller.UserController.class)
                .updateUsers(null, user.getId()))
                .withRel("update"));
        
        user.add(linkTo(methodOn(org.example.user_service.controller.UserController.class)
                .deleteUsers(user.getId()))
                .withRel("delete"));
        
        user.add(linkTo(methodOn(org.example.user_service.controller.UserController.class)
                .getAllUsers())
                .withRel("users"));
        
        return user;
    }

    public CollectionModel<UserResponseDto> addLinksToCollection(List<UserResponseDto> users) {
        users.forEach(this::addLinks);
        
        // Создаем коллекцию с ссылками
        CollectionModel<UserResponseDto> collection = CollectionModel.of(users);
        
        collection.add(linkTo(methodOn(org.example.user_service.controller.UserController.class)
                .createUsers(null))
                .withRel("create"));
        
        collection.add(linkTo(methodOn(org.example.user_service.controller.UserController.class)
                .getAllUsers())
                .withSelfRel());
        
        return collection;
    }
} 