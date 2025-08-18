package com.github.italoalcantaraa.todolistapi.validation;

import com.github.italoalcantaraa.todolistapi.dto.user.CreateUserRequestDto;
import com.github.italoalcantaraa.todolistapi.dto.user.LoginRequestDto;
import com.github.italoalcantaraa.todolistapi.exception.user.InvalidDataUserException;
import com.github.italoalcantaraa.todolistapi.exception.user.InvalidLoginDataException;
import com.github.italoalcantaraa.todolistapi.exception.user.UserNotFoundExeption;
import com.github.italoalcantaraa.todolistapi.model.User;
import com.github.italoalcantaraa.todolistapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserValidation extends Validation{

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public void create(CreateUserRequestDto request) {
        if(request.username().length() < 3) {
            throw new InvalidDataUserException("O nome deve ter no mínimo 3 caracteres");
        }

        Optional<User> user = userRepository.findByUsername(request.username());

        if(user.isPresent()) {
            throw new InvalidDataUserException("Não foi possível concluir o cadastro. Verifique os dados e tente novamente.");
        }

        if(!validatePasswordCreation(request.password())) {
            throw new InvalidDataUserException("Mín. 8 caracteres com maiúscula, minúscula e número");
        }
    }

    public boolean validatePasswordCreation(String password) {
        if(password.length() < 8) {
            return false;
        }

        boolean uppercaseLetter = false;
        boolean lowercaseLetter = false;
        boolean specialCharacter = false;
        boolean digit = false;

        for(char c : password.toCharArray()) {
            if(Character.isUpperCase(c)) {
                uppercaseLetter = true;
            } else if(Character.isLowerCase(c)) {
                lowercaseLetter = true;
            } else if(Character.isDigit(c)) {
                digit = true;
            } else if(!Character.isLetterOrDigit(c)) {
                specialCharacter = true;
            }
        }

        return uppercaseLetter && lowercaseLetter && specialCharacter && digit;
    }

    public User loginValidation(LoginRequestDto request) {
        if(isNullOrIsEmpty(request.username(), request.password())) {
            throw new InvalidLoginDataException("Informe os campos");
        }

        // Mensagem para todos os erros de informações de login
        String message = "Usuário e/ou senha inválidos";

        Optional<User> user = userRepository.findByUsername(request.username());
        if(user.isEmpty()) {
            throw new UserNotFoundExeption(message);
        }

        // verificação de senha
        if(!passwordEncoder.matches(request.password(), user.get().getPassword())) {
            throw new InvalidLoginDataException(message);
        }

        return user.get(); // login ok
    }

    public boolean passwordValidation(String passwordRequest, String decodedPasswordSaved) {
        return passwordEncoder.matches(passwordRequest, decodedPasswordSaved);
    }
}
