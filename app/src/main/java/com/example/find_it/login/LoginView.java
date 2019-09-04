package com.example.find_it.login;

import com.example.find_it.login.model.ErrorResponse;
import com.example.find_it.login.model.LoginResponse;

public interface LoginView {
    void onLoginSuccess(LoginResponse response);

    void onLoginFailure(ErrorResponse errorResponse);

    void onNetworkError();
}
