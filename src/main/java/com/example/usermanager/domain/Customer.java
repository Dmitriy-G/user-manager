package com.example.usermanager.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;
import java.util.Objects;

import static com.example.usermanager.validator.ValidatorConstants.*;

@Document(collection = "customers")
@Validated
public class Customer {

    @Transient
    public static final String CUSTOMER_ID_SEQUENCE = "customer_sequence";

    @Id
    @Digits(integer=ID_MAX_DIGITS, fraction=ID_FRACTION)
    private Long customerId;

    @Indexed(unique=true)
    @NonNull
    @Size(max=LOGIN_MAX_LENGTH)
    @Pattern(regexp = LOGIN_REGEX)
    private String login;

    @NonNull
    @Size(min=PASSWORD_MIN_LENGTH)
    @Pattern(regexp = PASSWORD_REGEX)
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
