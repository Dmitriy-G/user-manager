package com.example.usermanager.service.impl;

import com.example.usermanager.domain.Customer;
import com.example.usermanager.domain.CustomerStatus;
import com.example.usermanager.exceptions.CustomerDataAlreadyUsed;
import com.example.usermanager.exceptions.CustomerNotFoundException;
import com.example.usermanager.repository.CustomerRepository;
import com.example.usermanager.service.*;
import com.mongodb.MongoWriteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDetailsService customerDetailsService;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoderService passwordEncoderService;
    private final MqService mqService;

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerDetailsService customerDetailsService, JwtTokenService jwtTokenService, PasswordEncoderService passwordEncoderService, MqService mqService) {
        this.customerRepository = customerRepository;
        this.customerDetailsService = customerDetailsService;
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoderService = passwordEncoderService;
        this.mqService = mqService;
    }

    @Override
    public String login(String login, String password) {
        var customer = findByLogin(login);
        if (!customerDetailsService.isValidCustomerForAuthentication(customer, password)){
            throw new CustomerNotFoundException("Customer with login = " + login + " is incorrect for authentication");
        }
        return jwtTokenService.generateToken(login);
    }

    @Override
    public void signUp(String login, String password, String email) {
        try {
            var savedCustomer = customerRepository.save(new Customer(login, passwordEncoderService.encode(password), CustomerStatus.NonActive, email));
            log.info("Registration new customer " + login);
            mqService.activateCustomer(savedCustomer.getCustomerId());
        } catch (MongoWriteException e) {
            throw new CustomerDataAlreadyUsed("Login or email already used", e);
        }
    }

    @Override
    public void changeCustomerStatus(Long id, CustomerStatus newStatus) {
        var customer = customerRepository.findByCustomerId(id);
        if (Objects.isNull(customer)){
            throw new CustomerNotFoundException("Customer with id = " + id + " is not found");
        }
        log.info("Change status for " + customer.getLogin() + " from " + customer.getStatus() + " to " + newStatus);
        customer.setStatus(newStatus);
        customerRepository.save(customer);
    }

    @Override
    public Customer findByLogin(String login) {
        return customerRepository.findByLogin(login);
    }
}
