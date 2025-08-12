package com.github.italoalcantaraa.todolistapi.validation;

import org.springframework.stereotype.Component;

@Component
public class Validation {
    
    public boolean isNullOrIsEmpty(String ...values) {
        
        boolean empty = false;

        for(String value : values) {
            if(value.isEmpty() || value == " ") { 
                empty = true;
            }
        }

        return empty;
    }
}
