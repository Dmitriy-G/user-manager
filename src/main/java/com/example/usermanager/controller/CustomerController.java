package com.example.usermanager.controller;

import com.example.usermanager.service.CustomerServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            @ApiResponse(code = 200, message = "Success")
    })
    @PostMapping(value = "/changeCustomerStatus")
    public void changeCustomerStatus() {

    }

    @ApiOperation(value = "Registration new customer")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success")
    })
    @PostMapping(value = "/registration")
    public void registration(@RequestParam String login, @RequestParam String password, @RequestParam String email){
        customerServiceImpl.registration(login, password, email);
    }

    /*@GetMapping(value = "/login")
    public String login() {
        return customerService.login("", "");
    }*/
}
