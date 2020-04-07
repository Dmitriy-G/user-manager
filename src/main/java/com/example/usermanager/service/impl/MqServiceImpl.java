package com.example.usermanager.service.impl;

import com.example.usermanager.config.ChangeCustomerStatusSender;
import com.example.usermanager.service.MqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqServiceImpl implements MqService {

    private final ChangeCustomerStatusSender changeCustomerStatusSender;

    @Autowired
    public MqServiceImpl(ChangeCustomerStatusSender changeCustomerStatusSender) {
        this.changeCustomerStatusSender = changeCustomerStatusSender;
    }

    @Override
    public void activateCustomer(Long customerId) {
        changeCustomerStatusSender.send(customerId);
    }
}
