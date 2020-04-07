package com.example.usermanager.config;

import com.example.usermanager.service.CustomerService;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class RabbitMQConfig {

    @Value("${customer_manager.rabbitmq.queue}")
    private String queueName;

    @Value("${customer_manager.rabbitmq.exchange}")
    private String exchange;

    @Value("${customer_manager.rabbitmq.routingKey}")
    private String routingKey;


    private final CustomerService customerService;

    @Autowired
    public RabbitMQConfig(@Lazy CustomerService customerService) {
        this.customerService = customerService;
    }

    @Bean
    Queue queue() {
        return new Queue(queueName);
    }

    @Bean
    DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(exchange)
                .build();
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(routingKey);
    }

    @Bean
    ChangeCustomerStatusReceiver receiver() {
        return new ChangeCustomerStatusReceiver(customerService);
    }

    @Bean
    ChangeCustomerStatusSender sender(AmqpTemplate template) {
        return new ChangeCustomerStatusSender(template);
    }

}
