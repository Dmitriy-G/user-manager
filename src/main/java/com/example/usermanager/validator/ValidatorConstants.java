package com.example.usermanager.validator;

public final class ValidatorConstants {

    // Constants for constraint annotations

    public static final String LOGIN_REGEX = "^[A-z0-9_.-]*$";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[A-z])(?=.*[!?@#$%^&+=]).*$";

    // A compile-time constant expression is an expression denoting a value of primitive type or a String, so no wrapper here because wrappers converts in runtime
    public static final int LOGIN_MAX_LENGTH = 36;
    public static final int PASSWORD_MIN_LENGTH = 8;

    public static final int ID_MAX_DIGITS = 8;
    public static final int ID_FRACTION = 0;


    //TODO: need to add here correct massages for validation errors

    private ValidatorConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}
