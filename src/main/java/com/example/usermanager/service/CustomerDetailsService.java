package com.example.usermanager.service;

import com.example.usermanager.domain.Customer;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.example.usermanager.validator.ValidatorConstants.PASSWORD_MIN_LENGTH;
import static com.example.usermanager.validator.ValidatorConstants.PASSWORD_REGEX;

@Validated
public interface CustomerDetailsService {
    boolean isValidCustomerStatus(Customer customer);
    boolean isValidCustomerForAuthentication(Customer customer, @Size(min=PASSWORD_MIN_LENGTH) @Pattern(regexp = PASSWORD_REGEX) String password);
}
