package com.example.find_it.login.model;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse {
    private Errors errors;

    public static class Errors {
        @SerializedName("email")
        private String emailError;
        @SerializedName("password")
        private String passwordError;

        public String getEmailError() {
            return emailError;
        }

        public String getPasswordError() {
            return passwordError;
        }
    }

    public Errors getErrors() {
        return errors;
    }
}
