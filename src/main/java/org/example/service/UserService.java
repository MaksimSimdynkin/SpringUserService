package org.example.service;

import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;


public class UserService  {
    List<UserResponseDto> getAllUsers();
    UserRequestDto getUsersById();
    UserResponseDto createUser(UserRequestDto userRequestDto);
    UserResponseDto updateUser(Long id, UserRequestDto userRequestDto);
    void deleteUser(Long id);
}
