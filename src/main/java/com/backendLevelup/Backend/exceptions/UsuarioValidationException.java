package com.backendLevelup.Backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UsuarioValidationException extends RuntimeException {
    public UsuarioValidationException(String message) {
        super(message);
    }
}
