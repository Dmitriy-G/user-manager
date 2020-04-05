package com.example.usermanager.service;

import com.example.usermanager.domain.CustomerStatus;
import com.example.usermanager.validators.Email;
import com.example.usermanager.validators.Login;
import com.example.usermanager.validators.Password;
import org.springframework.validation.annotation.Validated;

import java.math.BigInteger;

@Validated
public interface CustomerService {

    boolean isAuthenticated(@Login String login, @Password String password);
    void registration(@Login String login, @Password String password, @Email String email);
    void changeCustomerStatus(BigInteger customerId, CustomerStatus newStatus);

}
