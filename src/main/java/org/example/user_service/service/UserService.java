package org.example.user_service.service;

import org.example.user_service.dto.UserRequestDto;
import org.example.user_service.dto.UserResponseDto;

import java.util.List;


public interface UserService  {
    List<UserResponseDto> getAllUsers();
    UserResponseDto getUsersById(Long id);
    UserResponseDto createUser(UserRequestDto userRequestDto);
    UserResponseDto updateUser(Long id, UserRequestDto userRequestDto);
    void deleteUser(Long id);


}
