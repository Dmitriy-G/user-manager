package com.example.usermanager.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class RabbitMQSender {

    private final AmqpTemplate rabbitTemplate;

    private static final Logger log = LoggerFactory.getLogger(RabbitMQSender.class);

    @Value("${customer_manager.rabbitmq.exchange}")
    private String exchange;

    @Value("${customer_manager.rabbitmq.routingKey}")
    private String routingKey;

    @Autowired
    public RabbitMQSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(BigInteger customerId) {
        rabbitTemplate.convertAndSend(exchange, routingKey, customerId);
        log.info("Change status to Active is planned for customer with id " + customerId);
    }

}
