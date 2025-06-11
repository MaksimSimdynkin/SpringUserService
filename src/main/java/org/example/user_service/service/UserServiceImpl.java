package org.example.user_service.service;


import jakarta.transaction.Transactional;
import org.example.user_service.dto.UserRequestDto;
import org.example.user_service.dto.UserResponseDto;
import org.example.user_service.entity.User;
import org.example.user_service.kafka.UserEventProducer;
import org.example.user_service.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final UserEventProducer userEventProducer;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, UserEventProducer userEventProducer) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.userEventProducer = userEventProducer;
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
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())){
            throw new RuntimeException("Электронная почта уже существует");
        }
        User user = modelMapper.map(userRequestDto, User.class);
        user.setCreatedat(LocalDateTime.now());
        User userCreated = userRepository.save(user);

        userEventProducer.sendUserCreatedEvent(userCreated.getEmail());

        return modelMapper.map(userCreated, UserResponseDto.class);
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
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь с id " + id + " не найден"));
        String email = user.getEmail();
        userRepository.deleteById(id);

        userEventProducer.sendUserDeletedEvent(email);

    }



}
