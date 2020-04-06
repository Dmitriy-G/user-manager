package com.example.usermanager.service;

import com.example.usermanager.validators.Login;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface JwtTokenService {
    String generateToken(@Login String login);
    Jws<Claims> parseToken(String token);
}
