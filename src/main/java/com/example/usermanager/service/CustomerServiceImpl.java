package com.example.usermanager.service;

import com.example.usermanager.domain.Customer;
import com.example.usermanager.domain.CustomerStatus;
import com.example.usermanager.mq.RabbitMQSender;
import com.example.usermanager.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final RabbitMQSender rabbitMQSender;

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, RabbitMQSender rabbitMQSender) {
        this.customerRepository = customerRepository;
        this.rabbitMQSender = rabbitMQSender;
    }

    @Override
    public boolean isAuthenticated(String login, String password) {
        var customer = customerRepository.findByLogin(login);
        log.info("Check " + login + " before authentication");
        return !Objects.isNull(customer) && customer.getStatus() == CustomerStatus.Active && passwordEncoder.matches(password, customer.getPassword());
    }

    @Override
    public void registration(String login, String password, String email) {
        Customer customer = customerRepository.save(new Customer(login, passwordEncoder.encode(password), CustomerStatus.NonActive, email));
        log.info("Registration new customer " + login);
        rabbitMQSender.send(customer.getCustomerId());
    }

    @Override
    public void changeCustomerStatus(BigInteger id, CustomerStatus newStatus) {
        var customer = customerRepository.findByCustomerId(id);
        if (Objects.isNull(customer)){
            throw new RuntimeException();
        }
        log.info("Change status for " + customer.getLogin() + " from " + customer.getStatus() + " to " + newStatus);
        customer.setStatus(newStatus);
    }

}
