package com.github.italoalcantaraa.todolistapi.controller;

import com.github.italoalcantaraa.todolistapi.dto.user.CreateUserResquestDto;
import com.github.italoalcantaraa.todolistapi.service.UserService;
import com.github.italoalcantaraa.todolistapi.validation.UserValidation;
import jakarta.persistence.GeneratedValue;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void create(@RequestBody CreateUserResquestDto createUserResquestDto) {
        System.out.println(createUserResquestDto.password());
        userService.create(createUserResquestDto);
    }

    @GetMapping
    public String getUserById() {
        return "Ola!";
    }
}
