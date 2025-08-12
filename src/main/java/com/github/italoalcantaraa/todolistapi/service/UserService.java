package com.github.italoalcantaraa.todolistapi.service;

import com.github.italoalcantaraa.todolistapi.dto.user.CreateUserResquestDto;
import com.github.italoalcantaraa.todolistapi.dto.user.UserResponseDto;
import com.github.italoalcantaraa.todolistapi.model.User;
import com.github.italoalcantaraa.todolistapi.repository.UserRepository;
import com.github.italoalcantaraa.todolistapi.validation.UserValidation;
import lombok.AllArgsConstructor;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private UserValidation userValidation;
    private PasswordEncoder passwordEncoder;

    public void create(CreateUserResquestDto createUserResquestDto) {
        userValidation.create(createUserResquestDto);

        User user = new User();
        user.setUsername(createUserResquestDto.username());
        user.setPassword(passwordEncoder.encode(createUserResquestDto.password()));

        userRepository.save(user);
    }

    public Optional<UserResponseDto> findById(String id) {
        
    }
}
