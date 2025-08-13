package com.github.italoalcantaraa.todolistapi.validation;

import com.github.italoalcantaraa.todolistapi.dto.user.CreateUserResquestDto;
import com.github.italoalcantaraa.todolistapi.model.User;
import com.github.italoalcantaraa.todolistapi.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserValidation extends Validation{

    private UserRepository userRepository;

    public UserValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void create(CreateUserResquestDto createUserResquestDto) {
        if(createUserResquestDto.username().length() < 4) {
            throw new IllegalArgumentException("Mín. 4 caracteres.");
        }

        Optional<User> user = userRepository.findByUsername(createUserResquestDto.username());

        if(user.isPresent()) {
            throw new IllegalArgumentException("Não foi possível concluir o cadastro. Verifique os dados e tente novamente.");
        }

        if(!passwordValidation(createUserResquestDto.password())) {
            throw new IllegalArgumentException("Mín. 8 caracteres com maiúscula, minúscula e número");
        }
    }

    public boolean passwordValidation(String password) {
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

}
