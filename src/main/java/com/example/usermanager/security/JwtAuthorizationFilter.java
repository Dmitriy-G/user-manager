package com.example.usermanager.security;

import com.example.usermanager.service.CustomerDetailsService;
import com.example.usermanager.service.CustomerService;
import com.example.usermanager.service.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.example.usermanager.security.SecurityConstants.*;

public class JwtAuthorizationFilter extends GenericFilterBean {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    private final CustomerService customerService;
    private final JwtTokenService jwtTokenService;
    private final CustomerDetailsService customerDetailsService;


    public JwtAuthorizationFilter(CustomerService customerService, JwtTokenService jwtTokenService, CustomerDetailsService customerDetailsService) {
        this.customerService = customerService;
        this.jwtTokenService = jwtTokenService;
        this.customerDetailsService = customerDetailsService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                            FilterChain filterChain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) request;
        var token = httpRequest.getHeader(TOKEN_HEADER);
        var parsedToken = jwtTokenService.parseToken(token);
        if (Objects.nonNull(parsedToken)) {
            var login = parsedToken.getBody().getSubject();
            var customer = customerService.findByLogin(login);
            if (customerDetailsService.isValidCustomerStatus(customer)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(customer, null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            }
        }
        ((HttpServletResponse) response).setStatus(403);

    }
}
