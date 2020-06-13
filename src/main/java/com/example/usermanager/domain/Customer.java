package com.example.usermanager.domain;

import lombok.*;
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

import static com.example.usermanager.validator.ValidatorConstants.*;

@Document(collection = "customers")
@Validated
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
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

    @PersistenceConstructor
    public Customer(String login, String password, CustomerStatus status, String email) {
        this.login = login;
        this.password = password;
        this.status = status;
        this.email = email;
    }

}
