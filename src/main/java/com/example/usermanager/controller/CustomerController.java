package com.example.usermanager.controller;

import com.example.usermanager.domain.CustomerStatus;
import com.example.usermanager.service.impl.CustomerServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/api")
@Api("Customer manage")
public class CustomerController {

    private final CustomerServiceImpl customerServiceImpl;

    @Autowired
    public CustomerController(CustomerServiceImpl customerServiceImpl) {
        this.customerServiceImpl = customerServiceImpl;
    }


    @ApiOperation(value = "Change customer status")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 403, message = "Access denied")
    })
    @PostMapping(value = "/changeCustomerStatus")
    public void changeCustomerStatus(@RequestParam BigInteger customerId, @RequestParam String newStatus) {
         customerServiceImpl.changeCustomerStatus(customerId, CustomerStatus.valueOf(newStatus));
    }

    @ApiOperation(value = "Registration new customer")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PostMapping(value = "/signup")
    public void registration(@RequestParam String login, @RequestParam String password, @RequestParam String email){
        customerServiceImpl.signup(login, password, email);
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam String login, @RequestParam String password) {
        return customerServiceImpl.login(login, password);
    }
}
