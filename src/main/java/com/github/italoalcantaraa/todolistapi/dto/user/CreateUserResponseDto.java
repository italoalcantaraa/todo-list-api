package com.github.italoalcantaraa.todolistapi.dto.user;

import com.github.italoalcantaraa.todolistapi.model.User;

import java.util.UUID;

public record CreateUserResponseDto(UUID id, String name, String token) {

    public static CreateUserResponseDto fromEntity(User user, String token) {
        return new CreateUserResponseDto(
                user.getId(),
                user.getUsername(),
                token);
    }
}
