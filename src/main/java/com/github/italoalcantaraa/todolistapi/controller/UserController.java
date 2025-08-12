package com.github.italoalcantaraa.todolistapi.controller;

import com.github.italoalcantaraa.todolistapi.dto.user.CreateUserResquestDto;
import com.github.italoalcantaraa.todolistapi.dto.user.UserResponseDto;
import com.github.italoalcantaraa.todolistapi.service.UserService;
import com.github.italoalcantaraa.todolistapi.validation.UserValidation;
import jakarta.persistence.GeneratedValue;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
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

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
        try {
            Optional<UserResponseDto> userOpt = userService.findById(id);

            if(userOpt.isPresent()) {
                return ResponseEntity.ok().body(userOpt.get().name());
            }

            return ResponseEntity.status(404).body("Usuário não encontrado");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
