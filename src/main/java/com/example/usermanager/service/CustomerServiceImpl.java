package com.example.usermanager.service;

import com.example.usermanager.domain.Customer;
import com.example.usermanager.domain.CustomerStatusEnum;
import com.example.usermanager.repository.CustomerRepository;
import com.example.usermanager.validators.Email;
import com.example.usermanager.validators.Login;
import com.example.usermanager.validators.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

   public boolean isAuthenticated(String login, String password){
       Customer customer = customerRepository.findByLogin(login);
       return !Objects.isNull(customer) && customer.getStatus() == CustomerStatusEnum.Active && passwordEncoder.matches(password, customer.getPassword());
   }

    public void registration(String login, String password, String email){
        customerRepository.save(new Customer(login, passwordEncoder.encode(password), CustomerStatusEnum.NonActive, email));
    }

}
