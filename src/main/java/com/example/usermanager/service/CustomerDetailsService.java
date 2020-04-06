package com.example.usermanager.service;

import com.example.usermanager.domain.Customer;
import com.example.usermanager.validators.Password;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CustomerDetailsService {
    boolean isValidCustomerStatus(Customer customer);
    boolean isValidCustomerForAuthentication(Customer customer, @Password String password);
}
