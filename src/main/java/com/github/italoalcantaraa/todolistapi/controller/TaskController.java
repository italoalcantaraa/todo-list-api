package com.github.italoalcantaraa.todolistapi.controller;

import com.github.italoalcantaraa.todolistapi.dto.task.CreateTaskRequestDto;
import com.github.italoalcantaraa.todolistapi.dto.task.CreateTaskResponseDto;
import com.github.italoalcantaraa.todolistapi.dto.task.TaskResponseDto;
import com.github.italoalcantaraa.todolistapi.model.Task;
import com.github.italoalcantaraa.todolistapi.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@AllArgsConstructor
public class TaskController {

    private TaskService taskService;

    @PostMapping
    public ResponseEntity<CreateTaskResponseDto> createTask(@RequestBody  CreateTaskRequestDto createTaskRequestDto) {
        CreateTaskResponseDto response = taskService.createTask(createTaskRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> findAllTasks() {
        List<TaskResponseDto> tasks = taskService.findAll();
        return ResponseEntity.ok(tasks);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeTask(@PathVariable("id") String id) {
        taskService.removeTask(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping
    public ResponseEntity<Void> editTask(@RequestBody Task task) {
        taskService.edit(task);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
