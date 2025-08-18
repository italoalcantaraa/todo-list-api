package com.github.italoalcantaraa.todolistapi.controller;

import com.github.italoalcantaraa.todolistapi.dto.user.CreateUserRequestDto;
import com.github.italoalcantaraa.todolistapi.dto.user.CreateUserResponseDto;
import com.github.italoalcantaraa.todolistapi.dto.user.LoginRequestDto;
import com.github.italoalcantaraa.todolistapi.dto.user.LoginResponseDto;
import com.github.italoalcantaraa.todolistapi.service.UserService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto response = userService.login(loginRequestDto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
    }

    @PostMapping("/register")
    public ResponseEntity<CreateUserResponseDto> create(@RequestBody CreateUserRequestDto createUserRequestDto) {
            CreateUserResponseDto user = userService.create(createUserRequestDto);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
