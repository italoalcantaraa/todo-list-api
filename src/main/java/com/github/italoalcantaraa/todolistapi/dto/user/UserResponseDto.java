package com.github.italoalcantaraa.todolistapi.dto.user;

import com.github.italoalcantaraa.todolistapi.model.User;

public record UserResponseDto(String name) {

    public static UserResponseDto fromEntity(User user) {
        return new UserResponseDto(user.getUsername());
    }
}
