package com.example.usermanager.mq;

import com.example.usermanager.domain.CustomerStatus;
import com.example.usermanager.service.impl.CustomerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class ChangeCustomerStatusReceiver {

    private static final Logger log = LoggerFactory.getLogger(ChangeCustomerStatusReceiver.class);

    private final CustomerServiceImpl customerService;

    @Autowired
    public ChangeCustomerStatusReceiver(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    public void receiveMessage(String message) {
        log.info("Message with customerId = " + message + " was received. Start process for change status to Active.");
        customerService.changeCustomerStatus(new BigInteger(message), CustomerStatus.Active);
    }
}
