package com.github.italoalcantaraa.todolistapi.service;

import com.github.italoalcantaraa.todolistapi.dto.task.CreateTaskRequestDto;
import com.github.italoalcantaraa.todolistapi.dto.task.CreateTaskResponseDto;
import com.github.italoalcantaraa.todolistapi.dto.task.TaskResponseDto;
import com.github.italoalcantaraa.todolistapi.exception.user.UserNotFoundExeption;
import com.github.italoalcantaraa.todolistapi.model.Task;
import com.github.italoalcantaraa.todolistapi.model.User;
import com.github.italoalcantaraa.todolistapi.repository.TaskRepository;
import com.github.italoalcantaraa.todolistapi.repository.UserRepository;
import com.github.italoalcantaraa.todolistapi.validation.TaskValidation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private TaskValidation taskValidation;

    public CreateTaskResponseDto createTask(CreateTaskRequestDto createTaskRequestDto) {
        taskValidation.create(createTaskRequestDto);

        // 1. Pega o username do token do usuário autenticado;
        String username = getUsernameAuthenticated(); // pega o username do usuário

        // 2. Obter usuário através do username;
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundExeption("Usuário não encontrado"));

        // 4. Crio a nova task depois de ter sido validada e associo ela ao usuário
        Task task = new Task(createTaskRequestDto.description(), user);

        // 4. Faço a persistência de dados
        Task savedTask = taskRepository.save(task);

        return CreateTaskResponseDto.fromEntity(task);
    }

    public List<TaskResponseDto> findAll() {
        String username = getUsernameAuthenticated();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundExeption("Usuário não encontrado"));

        List<TaskResponseDto> tasksReponse = user.getTasks()
                .stream().
                map(TaskResponseDto::fromEntity)
                .toList();

        return tasksReponse;
    }

    public void removeTask(String id) {
        Task taskRemove = taskValidation.remove(id);
        taskRepository.delete(taskRemove);
    }

    public void edit(Task newTask) {
        taskValidation.edit(newTask);
        taskRepository.save(newTask);
    }

    public String getUsernameAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
