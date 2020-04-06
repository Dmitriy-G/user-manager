package com.example.usermanager.service;

import com.example.usermanager.domain.Customer;
import com.example.usermanager.domain.CustomerStatus;
import com.example.usermanager.validators.Email;
import com.example.usermanager.validators.Login;
import com.example.usermanager.validators.Password;
import org.springframework.validation.annotation.Validated;

import java.math.BigInteger;

@Validated
public interface CustomerService {
    String login(@Login String login, @Password String password);
    void signup(@Login String login, @Password String password, @Email String email);
    void changeCustomerStatus(BigInteger customerId, CustomerStatus newStatus);
    Customer findByLogin(@Login String login);
}
