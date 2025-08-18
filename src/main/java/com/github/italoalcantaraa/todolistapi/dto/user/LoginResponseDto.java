package com.github.italoalcantaraa.todolistapi.dto.user;

import com.github.italoalcantaraa.todolistapi.model.User;

public record LoginResponseDto (String username, String token) {
    public static LoginResponseDto fromEntity(String username, String token) {
        return new LoginResponseDto(username, token);
    }
}
