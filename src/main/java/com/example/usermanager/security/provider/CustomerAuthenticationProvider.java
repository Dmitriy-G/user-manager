package com.example.usermanager.security.provider;

import com.example.usermanager.service.CustomerServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomerAuthenticationProvider implements AuthenticationProvider {


    private final CustomerServiceImpl customerServiceImpl;

    public CustomerAuthenticationProvider(CustomerServiceImpl customerServiceImpl) {
        this.customerServiceImpl = customerServiceImpl;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var name = authentication.getPrincipal().toString();
        var password = authentication.getCredentials().toString();

        if (!customerServiceImpl.isAuthenticated(name, password)) {
            throw new AuthenticationServiceException("Authentication failed");
        }

        return new
                UsernamePasswordAuthenticationToken(name, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
