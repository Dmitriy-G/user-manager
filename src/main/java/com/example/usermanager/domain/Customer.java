package com.example.usermanager.domain;

import com.example.usermanager.validators.Login;
import com.example.usermanager.validators.Password;
import com.example.usermanager.validators.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Document(collection = "customers")
@Validated
public class Customer {

    @Transient
    public static final String CUSTOMER_ID_SEQUENCE = "customer_sequence";

    @Id
    private Long customerId;

    @Indexed(unique=true)
    @NonNull
    @Login
    private String login;

    @NonNull
    @Password
    private String password;

    @NonNull
    private CustomerStatus status;

    @Indexed(unique=true)
    @NonNull
    @Email
    private String email;

    public Customer() {
    }

    @PersistenceConstructor
    public Customer(String login, String password, CustomerStatus status, String email) {
        this.login = login;
        this.password = password;
        this.status = status;
        this.email = email;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var customer = (Customer) o;
        return Objects.equals(getCustomerId(), customer.getCustomerId()) &&
                Objects.equals(getLogin(), customer.getLogin()) &&
                Objects.equals(getEmail(), customer.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerId(), getLogin(), getEmail());
    }




}
