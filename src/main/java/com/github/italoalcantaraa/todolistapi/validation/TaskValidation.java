package com.github.italoalcantaraa.todolistapi.validation;

import com.github.italoalcantaraa.todolistapi.dto.task.CreateTaskRequestDto;
import com.github.italoalcantaraa.todolistapi.exception.task.InvalidDataTaskException;
import com.github.italoalcantaraa.todolistapi.exception.task.TaskNotFoundException;
import com.github.italoalcantaraa.todolistapi.model.Task;
import com.github.italoalcantaraa.todolistapi.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class TaskValidation extends Validation {

    private TaskRepository taskRepository;

    public void create(CreateTaskRequestDto createTaskRequestDto) {
        if(isNullOrIsEmpty(createTaskRequestDto.description())) {
            throw new InvalidDataTaskException("Informe a tarefa");
        }
    }

    public Task remove(String id) {
        if(isNullOrIsEmpty(id)) {
            throw new InvalidDataTaskException("Informe a tarefa");
        }

        Optional<Task> taskOpt = taskRepository.findById(UUID.fromString(id));

        if(taskOpt.isEmpty()) {
            throw new TaskNotFoundException("Tarefa não encontrada");
        }

        return taskOpt.get();
    }

    public void edit(Task newTask) {
        if(isNullOrIsEmpty(newTask.getDescription(), newTask.getId().toString())) {
            throw new InvalidDataTaskException("Tarefa inválida");
        }

        Optional<Task> taskOpt = taskRepository.findById(newTask.getId());

        if(taskOpt.isEmpty()) {
            throw new TaskNotFoundException("Tarefa não encontrada");
        }
    }
}
