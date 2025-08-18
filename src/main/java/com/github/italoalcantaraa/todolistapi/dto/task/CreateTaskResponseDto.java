package com.github.italoalcantaraa.todolistapi.dto.task;

import com.github.italoalcantaraa.todolistapi.model.Task;

import java.util.UUID;

public record CreateTaskResponseDto(UUID id, String description) {
    public static CreateTaskResponseDto fromEntity(Task task) {
        return new CreateTaskResponseDto(task.getId(), task.getDescription());
    }
}
