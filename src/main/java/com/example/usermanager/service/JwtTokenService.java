package com.example.usermanager.service;

import com.example.usermanager.validators.Login;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.validation.annotation.Validated;

@Validated
public interface JwtTokenService {
    String generateToken(@Login String login);
    Jws<Claims> parseToken(String token);
}
