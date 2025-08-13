package com.github.italoalcantaraa.todolistapi.dto.user;

import com.github.italoalcantaraa.todolistapi.model.User;

import java.util.UUID;

public record UserResponseDto(UUID id, String name) {

    public static UserResponseDto fromEntity(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername());
    }
}
