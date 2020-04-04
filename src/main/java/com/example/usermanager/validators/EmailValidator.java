package com.example.usermanager.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<Email, String> {

    private final static Pattern pattern = Pattern.compile("^[_A-Za-z0-9-+]+ (.[_A-Za-z0-9-]+)*@\" + \"[A-Za-z0-9-]+(.[A-Za-z0-9]+)* (.[A-Za-z]{2,})$");

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return pattern.matcher(email).matches();
    }
}
