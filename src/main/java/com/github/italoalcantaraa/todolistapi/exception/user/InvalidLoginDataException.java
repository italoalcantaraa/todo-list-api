package com.github.italoalcantaraa.todolistapi.exception.user;

public class InvalidLoginDataException extends RuntimeException {
    public InvalidLoginDataException(String message) {
        super(message);
    }
}
