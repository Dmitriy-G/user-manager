package com.example.usermanager.service;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Digits;

import static com.example.usermanager.validator.ValidatorConstants.ID_FRACTION;
import static com.example.usermanager.validator.ValidatorConstants.ID_MAX_DIGITS;

@Validated
public interface MqService {
    void activateCustomer(@Digits(integer=ID_MAX_DIGITS, fraction=ID_FRACTION) Long customerId);
}
