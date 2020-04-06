package com.example.usermanager.service.impl;

import com.example.usermanager.service.PasswordEncoderService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderServiceImpl implements PasswordEncoderService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public Boolean matches(String passwordFromRequest, String decodedPassword) {
        return passwordEncoder.matches(passwordFromRequest, decodedPassword);
    }
}
