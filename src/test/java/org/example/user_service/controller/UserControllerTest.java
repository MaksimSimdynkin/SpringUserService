package org.example.user_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.user_service.dto.UserRequestDto;
import org.example.user_service.dto.UserResponseDto;
import org.example.user_service.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private UserResponseDto userResponseDto;
    private UserRequestDto validUserRequestDto;
    private UserRequestDto invalidUserRequestDto;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        userResponseDto = new UserResponseDto();
        userResponseDto.setId(1L);
        userResponseDto.setName("Test User");
        userResponseDto.setEmail("test@example.com");
        userResponseDto.setAge(25);
        userResponseDto.setCreatedat(LocalDateTime.now());

        validUserRequestDto = new UserRequestDto();
        validUserRequestDto.setName("Test User");
        validUserRequestDto.setEmail("test@example.com");
        validUserRequestDto.setAge(25);

        invalidUserRequestDto = new UserRequestDto();
        invalidUserRequestDto.setName("");
        invalidUserRequestDto.setEmail("invalid-email");
        invalidUserRequestDto.setAge(-1);
    }

//    @Test
//    void getAllUsers_ShouldReturnUsersList() throws Exception {
//        given(userService.getAllUsers()).willReturn(Collections.singletonList(userResponseDto));
//
//        mockMvc.perform(get("/users"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].id").value(1L))
//                .andExpect(jsonPath("$[0].name").value("Test User"));
//    }

//    @Test
//    void getUserById_WhenUserExists_ShouldReturnUser() throws Exception {
//        given(userService.getUsersById(1L)).willReturn(userResponseDto);
//
//        mockMvc.perform(get("/users/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.name").value("Test User"));
//    }

    @Test
    void getUserById_WhenUserNotExists_ShouldReturnNotFound() throws Exception {
        given(userService.getUsersById(1L)).willThrow(new RuntimeException("User not found"));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isNotFound());
    }

//    @Test
//    void createUser_WithValidData_ShouldReturnCreatedUser() throws Exception {
//        given(userService.createUser(any(UserRequestDto.class))).willReturn(userResponseDto);
//
//        mockMvc.perform(post("/users/save")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(validUserRequestDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.name").value("Test User"));
//    }

    @Test
    void createUser_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        UserRequestDto invalidRequest = new UserRequestDto();
        invalidRequest.setName("");
        invalidRequest.setEmail("invalid-email");
        invalidRequest.setAge(-1);

        mockMvc.perform(post("/users/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

//    @Test
//    void updateUser_WhenUserExists_ShouldReturnUpdatedUser() throws Exception {
//        given(userService.updateUser(anyLong(), any(UserRequestDto.class))).willReturn(userResponseDto);
//
//        mockMvc.perform(put("/users/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(validUserRequestDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.name").value("Test User"));
//    }

    @Test
    void updateUser_WhenUserNotExists_ShouldReturnNotFound() throws Exception {
        given(userService.updateUser(anyLong(), any(UserRequestDto.class)))
                .willThrow(new RuntimeException("User not found"));

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserRequestDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_WhenUserExists_ShouldReturnOk() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUser_WhenUserNotExists_ShouldReturnNotFound() throws Exception {
        doThrow(new RuntimeException("User not found")).when(userService).deleteUser(1L);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNotFound());
    }
}