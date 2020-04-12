package com.example.usermanager.service;

import com.example.usermanager.domain.Customer;
import com.example.usermanager.domain.CustomerStatus;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.example.usermanager.validator.ValidatorConstants.*;

@Validated
public interface CustomerService {
    String login(@Size(max=LOGIN_MAX_LENGTH) @Pattern(regexp = LOGIN_REGEX) String login, @Size(min=PASSWORD_MIN_LENGTH) @Pattern(regexp = PASSWORD_REGEX)String password);
    void signUp(@Size(max=LOGIN_MAX_LENGTH) @Pattern(regexp = LOGIN_REGEX) String login, @Size(min=PASSWORD_MIN_LENGTH) @Pattern(regexp = PASSWORD_REGEX) String password, @Email String email);
    void changeCustomerStatus(@Digits(integer=ID_MAX_DIGITS, fraction=ID_FRACTION)Long customerId, CustomerStatus newStatus);
    Customer findByLogin(@Size(max=LOGIN_MAX_LENGTH) @Pattern(regexp = LOGIN_REGEX) String login);
}
