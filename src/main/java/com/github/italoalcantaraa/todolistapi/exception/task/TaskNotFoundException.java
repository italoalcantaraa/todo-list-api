package com.github.italoalcantaraa.todolistapi.exception.task;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
