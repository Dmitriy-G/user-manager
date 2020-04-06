package com.example.usermanager.service.impl;

import com.example.usermanager.domain.Customer;
import com.example.usermanager.domain.CustomerStatus;
import com.example.usermanager.exceptions.CustomerNotFoundException;
import com.example.usermanager.externallayers.RabbitMQSender;
import com.example.usermanager.repository.CustomerRepository;
import com.example.usermanager.service.CustomerDetailsService;
import com.example.usermanager.service.CustomerService;
import com.example.usermanager.service.JwtTokenService;
import com.example.usermanager.service.PasswordEncoderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final RabbitMQSender rabbitMQSender;
    private final CustomerDetailsService customerDetailsService;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoderService passwordEncoderService;

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, RabbitMQSender rabbitMQSender, CustomerDetailsService customerDetailsService, JwtTokenService jwtTokenService, PasswordEncoderService passwordEncoderService) {
        this.customerRepository = customerRepository;
        this.rabbitMQSender = rabbitMQSender;
        this.customerDetailsService = customerDetailsService;
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoderService = passwordEncoderService;
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
        var customer = customerRepository.save(new Customer(login, passwordEncoderService.encode(password), CustomerStatus.NonActive, email));
        log.info("Registration new customer " + login);
        rabbitMQSender.send(customer.getCustomerId());
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
