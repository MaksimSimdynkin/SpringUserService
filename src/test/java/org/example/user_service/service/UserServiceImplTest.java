package org.example.user_service.service;

import org.example.user_service.dto.UserRequestDto;
import org.example.user_service.dto.UserResponseDto;
import org.example.user_service.entity.User;
import org.example.user_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserRequestDto userRequestDto;
    private UserResponseDto userResponseDto;

    @BeforeEach
    void setUp() {
        user = new User("Test User", "test@example.com", 25);
        user.setId(1L);
        user.setCreatedat(LocalDateTime.now());

        userRequestDto = new UserRequestDto();
        userRequestDto.setName("Test User");
        userRequestDto.setEmail("test@example.com");
        userRequestDto.setAge(25);

        userResponseDto = new UserResponseDto();
        userResponseDto.setId(1L);
        userResponseDto.setName("Test User");
        userResponseDto.setEmail("test@example.com");
        userResponseDto.setAge(25);
        userResponseDto.setCreatedat(LocalDateTime.now());
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(modelMapper.map(user, UserResponseDto.class)).thenReturn(userResponseDto);

        List<UserResponseDto> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userResponseDto, result.get(0));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUsersById_WhenUserExists_ShouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserResponseDto.class)).thenReturn(userResponseDto);

        UserResponseDto result = userService.getUsersById(1L);

        assertNotNull(result);
        assertEquals(userResponseDto, result);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUsersById_WhenUserNotExists_ShouldThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUsersById(1L));
        verify(userRepository, times(1)).findById(1L);
    }

//    @Test
//    void createUser_WithValidData_ShouldReturnCreatedUser() {
//        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
//        when(modelMapper.map(userRequestDto, User.class)).thenReturn(user);
//        when(userRepository.save(user)).thenReturn(user);
//        when(modelMapper.map(user, UserResponseDto.class)).thenReturn(userResponseDto);
//
//        UserResponseDto result = userService.createUser(userRequestDto);
//
//        assertNotNull(result);
//        assertEquals(userResponseDto, result);
//        verify(userRepository, times(1)).existsByEmail("test@example.com");
//        verify(userRepository, times(1)).save(user);
//    }

    @Test
    void createUser_WithExistingEmail_ShouldThrowException() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.createUser(userRequestDto));
        verify(userRepository, times(1)).existsByEmail("test@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUser_WhenUserExists_ShouldReturnUpdatedUser() {
        User updatedUser = new User("Updated User", "updated@example.com", 30);
        updatedUser.setId(1L);
        updatedUser.setCreatedat(user.getCreatedat());

        UserResponseDto updatedResponseDto = new UserResponseDto();
        updatedResponseDto.setId(1L);
        updatedResponseDto.setName("Updated User");
        updatedResponseDto.setEmail("updated@example.com");
        updatedResponseDto.setAge(30);
        updatedResponseDto.setCreatedat(user.getCreatedat());

        UserRequestDto updateRequest = new UserRequestDto();
        updateRequest.setName("Updated User");
        updateRequest.setEmail("updated@example.com");
        updateRequest.setAge(30);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        doAnswer(invocation -> {
            UserRequestDto source = invocation.getArgument(0);
            User destination = invocation.getArgument(1);
            destination.setName(source.getName());
            destination.setEmail(source.getEmail());
            destination.setAge(source.getAge());
            return null;
        }).when(modelMapper).map(updateRequest, user);

        when(userRepository.save(user)).thenReturn(updatedUser);

        when(modelMapper.map(updatedUser, UserResponseDto.class)).thenReturn(updatedResponseDto);

        UserResponseDto result = userService.updateUser(1L, updateRequest);

        assertNotNull(result);
        assertEquals(updatedResponseDto, result);
        verify(userRepository, times(1)).findById(1L);
        verify(modelMapper).map(updateRequest, user);
        verify(modelMapper).map(updatedUser, UserResponseDto.class);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateUser_WhenUserNotExists_ShouldThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.updateUser(1L, userRequestDto));
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }

//    @Test
//    void deleteUser_WhenUserExists_ShouldDeleteUser() {
//        when(userRepository.existsById(1L)).thenReturn(true);
//        doNothing().when(userRepository).deleteById(1L);
//
//        userService.deleteUser(1L);
//
//        verify(userRepository, times(1)).existsById(1L);
//        verify(userRepository, times(1)).deleteById(1L);
//    }

//    @Test
//    void deleteUser_WhenUserNotExists_ShouldThrowException() {
//        when(userRepository.existsById(1L)).thenReturn(false);
//
//        assertThrows(RuntimeException.class, () -> userService.deleteUser(1L));
//        verify(userRepository, times(1)).existsById(1L);
//        verify(userRepository, never()).deleteById(1L);
//    }
}