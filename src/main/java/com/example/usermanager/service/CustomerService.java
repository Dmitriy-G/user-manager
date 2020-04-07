package com.example.usermanager.service;

import com.example.usermanager.domain.Customer;
import com.example.usermanager.domain.CustomerStatus;
import com.example.usermanager.validators.Email;
import com.example.usermanager.validators.Login;
import com.example.usermanager.validators.Password;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CustomerService {
    String login(@Login String login, @Password String password);
    void signUp(@Login String login, @Password String password, @Email String email);
    void changeCustomerStatus(Long customerId, CustomerStatus newStatus);
    Customer findByLogin(@Login String login);
}
