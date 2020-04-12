package com.example.usermanager.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.example.usermanager.validator.ValidatorConstants.LOGIN_MAX_LENGTH;
import static com.example.usermanager.validator.ValidatorConstants.LOGIN_REGEX;

@Validated
public interface JwtTokenService {
    String generateToken(@Size(max=LOGIN_MAX_LENGTH) @Pattern(regexp = LOGIN_REGEX) String login);
    Jws<Claims> parseToken(String token);
}
