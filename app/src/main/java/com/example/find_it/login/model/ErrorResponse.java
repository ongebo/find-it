package com.example.find_it.login.model;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse {
    private Errors errors;

    public static class Errors {
        @SerializedName("email")
        private String emailError;

        @SerializedName("password")
        private String passwordError;

        public Errors(String emailError, String passwordError) {
            this.emailError = emailError;
            this.passwordError = passwordError;
        }

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

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ErrorResponse) {
            boolean objectsEqual;
            ErrorResponse comparedObj = (ErrorResponse) obj;
            String emailError = this.errors.getEmailError();
            String passwordError = this.errors.getPasswordError();
            if (emailError != null) {
                objectsEqual = emailError.equals(comparedObj.getErrors().getEmailError());
            } else {
                objectsEqual = comparedObj.getErrors().getEmailError() == null;
            }
            if (passwordError != null && objectsEqual) {
                objectsEqual = passwordError.equals(comparedObj.getErrors().getEmailError());
            }
            return objectsEqual;
        } else {
            return false;
        }
    }
}
