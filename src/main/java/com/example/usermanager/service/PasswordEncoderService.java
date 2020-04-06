package com.example.usermanager.service;

import com.example.usermanager.validators.Password;

public interface PasswordEncoderService {
    String encode(@Password String password);
    Boolean matches(@Password String passwordFromRequest, String decodedPassword);
}
