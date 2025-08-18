package com.github.italoalcantaraa.todolistapi.exception.user;

public class UserNotFoundExeption extends RuntimeException {
    public UserNotFoundExeption(String message) {
        super(message);
    }
}
