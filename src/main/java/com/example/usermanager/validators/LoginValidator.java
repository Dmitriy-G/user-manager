package com.example.usermanager.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class LoginValidator implements ConstraintValidator<Login, String> {

    private Login annotation;
    private static final Pattern pattern = Pattern.compile("^[A-z0-9_.-]*$");

    @Override
    public void initialize(Login annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String login, ConstraintValidatorContext constraintValidatorContext) {
        return login.length() < annotation.max() && pattern.matcher(login).matches();
    }
}
