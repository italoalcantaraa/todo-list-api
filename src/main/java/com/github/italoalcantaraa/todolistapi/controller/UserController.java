package com.github.italoalcantaraa.todolistapi.controller;

import com.github.italoalcantaraa.todolistapi.dto.user.CreateUserResquestDto;
import com.github.italoalcantaraa.todolistapi.dto.user.UserResponseDto;
import com.github.italoalcantaraa.todolistapi.service.UserService;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateUserResquestDto createUserResquestDto) {
        try {
            UserResponseDto user = userService.create(createUserResquestDto);
            return ResponseEntity.ok(user);
        }catch (IllegalArgumentException iae) {
            return ResponseEntity.badRequest().body(iae.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro inesperado");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
        try {
            UserResponseDto userResponse = userService.findById(id);
            return ResponseEntity.ok().body(userResponse);
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.status(404).body(iae.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro inesperado");
        }
    }
}
