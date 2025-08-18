package com.github.italoalcantaraa.todolistapi.dto.task;

import com.github.italoalcantaraa.todolistapi.model.Task;

public record TaskResponseDto(String id, String description, boolean finished) {
    public static TaskResponseDto fromEntity(Task task) {
        return new TaskResponseDto(
                task.getId().toString(),
                task.getDescription(),
                task.isFinished()
        );
    }
}
