package com.example.usermanager.repository;

import com.example.usermanager.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, Long> {

    Customer findByLogin(String login);
}
