package com.example.usermanager.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;

public class ChangeCustomerStatusSender {

    private final AmqpTemplate rabbitTemplate;

    private static final Logger log = LoggerFactory.getLogger(ChangeCustomerStatusSender.class);

    private static final Integer delayValue = 500000;

    @Value("${customer_manager.rabbitmq.exchange}")
    private String exchange;

    @Value("${customer_manager.rabbitmq.routingKey}")
    private String routingKey;

    public ChangeCustomerStatusSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(Long customerId) {
        rabbitTemplate.convertAndSend(exchange, routingKey, customerId, message -> {
            message.getMessageProperties().setDelay(delayValue);
            return message;
        });
    }
}
