package com.github.italoalcantaraa.todolistapi.service;

import com.github.italoalcantaraa.todolistapi.dto.user.CreateUserResquestDto;
import com.github.italoalcantaraa.todolistapi.dto.user.UserResponseDto;
import com.github.italoalcantaraa.todolistapi.model.User;
import com.github.italoalcantaraa.todolistapi.repository.UserRepository;
import com.github.italoalcantaraa.todolistapi.validation.UserValidation;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private UserValidation userValidation;
    private PasswordEncoder passwordEncoder;

    public UserResponseDto create(CreateUserResquestDto createUserResquestDto) {
        userValidation.create(createUserResquestDto);

        User user = new User();
        user.setUsername(createUserResquestDto.username());
        user.setPassword(passwordEncoder.encode(createUserResquestDto.password()));

        userRepository.save(user);

        return UserResponseDto.fromEntity(user);
    }

    public UserResponseDto findById(String id) {
        Optional<User> userOpt = userRepository.findById(UUID.fromString(id));
        if (userOpt.isPresent()) {
            return UserResponseDto.fromEntity(userOpt.get());
        }

        throw new IllegalArgumentException("Usuário não encontrado");
    }
}
