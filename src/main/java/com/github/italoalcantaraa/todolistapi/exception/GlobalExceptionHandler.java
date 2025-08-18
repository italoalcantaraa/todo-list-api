package com.github.italoalcantaraa.todolistapi.exception;

import com.github.italoalcantaraa.todolistapi.dto.error.ErrorResponse;
import com.github.italoalcantaraa.todolistapi.exception.task.InvalidDataTaskException;
import com.github.italoalcantaraa.todolistapi.exception.task.TaskNotFoundException;
import com.github.italoalcantaraa.todolistapi.exception.user.InvalidDataUserException;
import com.github.italoalcantaraa.todolistapi.exception.user.InvalidLoginDataException;
import com.github.italoalcantaraa.todolistapi.exception.user.UserNotFoundExeption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // oberva todas as exceções lançadas
public class GlobalExceptionHandler {

    // observa todas as exceções do PERSISTÊNCIA do usuário
    @ExceptionHandler(InvalidDataUserException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUserDataException(InvalidDataUserException exception) {
        ErrorResponse errorReponse = new ErrorResponse(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorReponse);
    }

    // exceção caso os dados sejam inválidos
    @ExceptionHandler(InvalidLoginDataException.class)
    public ResponseEntity<ErrorResponse> handleLoginDataException(InvalidLoginDataException exception) {
        ErrorResponse errorReponse = new ErrorResponse(exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorReponse);
    }

    // exceção caso o usuário não seja encontrado
    @ExceptionHandler(UserNotFoundExeption.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException (UserNotFoundExeption exception) {
        ErrorResponse errorReponse = new ErrorResponse(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorReponse);
    }

    // observa todas ao inserir/editar uma terefa
    @ExceptionHandler(InvalidDataTaskException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTaskDataException(InvalidDataTaskException exception) {
        ErrorResponse errorReponse = new ErrorResponse(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorReponse);
    }

    // caso não encontre a tarefa
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFoundException (TaskNotFoundException exception) {
        ErrorResponse errorReponse = new ErrorResponse(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorReponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Erro interno: " + exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExeption(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Erro inesperado: " + exception.getMessage()));
    }
}
