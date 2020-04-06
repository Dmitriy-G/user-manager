package com.example.usermanager.config;

import com.example.usermanager.security.JwtAuthorizationFilter;
import com.example.usermanager.service.CustomerDetailsService;
import com.example.usermanager.service.CustomerService;
import com.example.usermanager.service.impl.CustomerServiceImpl;
import com.example.usermanager.service.JwtTokenService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomerService customerService;
    private final JwtTokenService jwtTokenService;
    private final CustomerDetailsService customerDetailsService;

    public SpringSecurityConfig(CustomerServiceImpl customerService, JwtTokenService jwtTokenService, CustomerDetailsService customerDetailsService) {
        this.customerService = customerService;
        this.jwtTokenService = jwtTokenService;
        this.customerDetailsService = customerDetailsService;
    }

    @Override
    public void configure(WebSecurity web){
        web.ignoring()
                .antMatchers("/api/login")
                .antMatchers("/api/signup")
                .antMatchers("/error");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthorizationFilter(customerService, jwtTokenService, customerDetailsService), UsernamePasswordAuthenticationFilter.class);

    }
}
