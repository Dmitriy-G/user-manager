package com.example.usermanager.service.impl;

import com.example.usermanager.domain.Customer;
import com.example.usermanager.domain.CustomerStatus;
import com.example.usermanager.service.CustomerDetailsService;
import com.example.usermanager.service.PasswordEncoderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomerDetailsServiceImpl implements CustomerDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomerDetailsServiceImpl.class);

    private final PasswordEncoderService passwordEncoderService;


    @Autowired
    public CustomerDetailsServiceImpl(PasswordEncoderService passwordEncoderService) {
       this.passwordEncoderService = passwordEncoderService;
    }

    @Override
    public boolean isValidCustomerStatus(Customer customer) {
        return !Objects.isNull(customer) && customer.getStatus() == CustomerStatus.Active;
    }

    @Override
    public boolean isValidCustomerForAuthentication(Customer customer, String passwordFromRequest) {
        log.info("Check " + customer.getLogin() + " before authentication");
        return isValidCustomerStatus(customer) && passwordEncoderService.matches(passwordFromRequest, customer.getPassword());
    }
}
