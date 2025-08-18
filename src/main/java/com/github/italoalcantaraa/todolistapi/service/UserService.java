package com.github.italoalcantaraa.todolistapi.service;

import com.github.italoalcantaraa.todolistapi.dto.user.CreateUserRequestDto;
import com.github.italoalcantaraa.todolistapi.dto.user.CreateUserResponseDto;
import com.github.italoalcantaraa.todolistapi.dto.user.LoginRequestDto;
import com.github.italoalcantaraa.todolistapi.dto.user.LoginResponseDto;
import com.github.italoalcantaraa.todolistapi.model.User;
import com.github.italoalcantaraa.todolistapi.repository.UserRepository;
import com.github.italoalcantaraa.todolistapi.security.JwtUtil;
import com.github.italoalcantaraa.todolistapi.validation.UserValidation;
import lombok.AllArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private UserValidation userValidation;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;

    public CreateUserResponseDto create(CreateUserRequestDto createUserRequestDto) {
        userValidation.create(createUserRequestDto);

        User user = new User();
        user.setUsername(createUserRequestDto.username());
        user.setPassword(passwordEncoder.encode(createUserRequestDto.password()));

        User savedUser = userRepository.save(user);

        String token = jwtUtil.generateToken(new org.springframework.security.core.userdetails.User(
           savedUser.getUsername(),
           savedUser.getPassword(),
           new ArrayList<>()
        ));

        return CreateUserResponseDto.fromEntity(savedUser, token);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        // autenticar o usuário
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.username(), loginRequestDto.password())
        );

        // carrega os dados o usuário
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDto.username());

        String token = jwtUtil.generateToken(userDetails);

        return LoginResponseDto.fromEntity(userDetails.getUsername(), token);
    }
}
