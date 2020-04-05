package com.example.usermanager.repository;

import com.example.usermanager.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, BigInteger> {

    Customer findByLogin(String login);
    Customer findByCustomerId(BigInteger customerId);
}
