package com.example.usermanager.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    private Password annotation;
    private static final Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[A-z])(?=.*[!?@#$%^&+=]).*$");

    @Override
    public void initialize(Password annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return password.length() > annotation.min() && pattern.matcher(password).matches();
    }
}
