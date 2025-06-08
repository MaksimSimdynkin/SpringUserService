package org.example.user_service.controller;


import jakarta.validation.Valid;
import org.example.user_service.dto.UserRequestDto;
import org.example.user_service.dto.UserResponseDto;
import org.example.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        return userService.getUsersById(id);
    }

    @PostMapping("save")
    public UserResponseDto createUsers(@Valid @RequestBody UserRequestDto userRequestDto){
        return userService.createUser(userRequestDto);
    }

    @PutMapping("/{id}")
    public UserResponseDto updateUsers(@RequestBody UserRequestDto userRequestDto, @PathVariable Long id){
        return userService.updateUser(id, userRequestDto);
    }

    @DeleteMapping("/{id}")
    public void  deleteUsers(@PathVariable Long id){
        userService.deleteUser(id);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        if (ex.getMessage().contains("not found")) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
