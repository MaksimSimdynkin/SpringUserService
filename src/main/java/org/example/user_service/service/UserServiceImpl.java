package org.example.user_service.service;


import jakarta.transaction.Transactional;
import org.example.user_service.dto.UserRequestDto;
import org.example.user_service.dto.UserResponseDto;
import org.example.user_service.entity.User;
import org.example.user_service.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final UserKafkaProducer userKafkaProducer;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, UserKafkaProducer userKafkaProducer) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.userKafkaProducer = userKafkaProducer;
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto getUsersById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь с id " + id + " не найден"));
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    @CircuitBreaker(name = "kafkaService", fallbackMethod = "createUserFallback")
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())){
            throw new RuntimeException("Электронная почта уже существует");
        }
        User user = modelMapper.map(userRequestDto, User.class);
        user.setCreatedat(LocalDateTime.now());
        User userCreated = userRepository.save(user);
        userKafkaProducer.senderUserOperation("CREATE", user.getEmail());
        return modelMapper.map(userCreated, UserResponseDto.class);
    }

    public UserResponseDto createUserFallback(UserRequestDto userRequestDto, Throwable t) {
        throw new RuntimeException("Сервис временно недоступен. Попробуйте позже.");
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь с id " + id + " не найден"));

        modelMapper.map(userRequestDto, user);
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserResponseDto.class);
    }

    @Override
    @CircuitBreaker(name = "kafkaService", fallbackMethod = "deleteUserFallback")
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь с id " + id + " не найден"));

        userKafkaProducer.senderUserOperation("DELETE", user.getEmail());
        userRepository.deleteById(id);
    }

    public void deleteUserFallback(Long id, Throwable t) {
        throw new RuntimeException("Сервис временно недоступен. Попробуйте позже.");
    }



}
