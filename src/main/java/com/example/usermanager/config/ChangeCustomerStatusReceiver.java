package com.example.usermanager.config;

import com.example.usermanager.domain.CustomerStatus;
import com.example.usermanager.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class ChangeCustomerStatusReceiver {

    private static final Logger log = LoggerFactory.getLogger(ChangeCustomerStatusReceiver.class);

    private final CustomerService customerService;

    public ChangeCustomerStatusReceiver(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RabbitListener(queues = "${customer_manager.rabbitmq.queue}")
    public void receivedMessage(String message) {
        log.info("Message with customerId = " + message + " was received. Start process for change status to Active.");
        customerService.changeCustomerStatus(Long.parseLong(message), CustomerStatus.Active);
    }
}
