package com.example.usermanager.config;

import com.example.usermanager.domain.Customer;
import com.example.usermanager.service.MongoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomerModelListener extends AbstractMongoEventListener<Customer> {

    private final MongoDBService mongoDBService;

    @Autowired
    public CustomerModelListener(MongoDBService mongoDBService) {
        this.mongoDBService = mongoDBService;
    }


    @Override
    public void onBeforeConvert(BeforeConvertEvent<Customer> event) {
        if (Objects.isNull(event.getSource().getCustomerId())) {
            event.getSource().setCustomerId(mongoDBService.generateSequence(Customer.CUSTOMER_ID_SEQUENCE));
        }
    }
}
