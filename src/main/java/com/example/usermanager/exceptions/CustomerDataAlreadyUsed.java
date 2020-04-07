package com.example.usermanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomerDataAlreadyUsed extends RuntimeException {

    public CustomerDataAlreadyUsed(String message) {
        super(message);
    }

    public CustomerDataAlreadyUsed(String message, Throwable cause) {
        super(message, cause);
    }
}
