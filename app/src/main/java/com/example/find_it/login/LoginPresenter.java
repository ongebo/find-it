package com.example.find_it.login;

import android.support.annotation.NonNull;

import com.example.find_it.login.model.ErrorResponse;
import com.example.find_it.login.model.LoginData;
import com.example.find_it.login.model.LoginResponse;
import com.example.find_it.service.FindItAPI;
import com.example.find_it.service.FindItService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class LoginPresenter {
    private FindItAPI api;
    private LoginView view;

    LoginPresenter(LoginView view) {
        api = FindItService.getAPI();
        this.view = view;
    }

    void loginUser(LoginData loginData) {
        api.loginUser(loginData).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(
                    @NonNull Call<LoginResponse> call,
                    @NonNull Response<LoginResponse> response
            ) {
                if (response.isSuccessful()) {
                    view.onLoginSuccess(response.body());
                } else {
                    try {
                        JsonElement loginJsonError = new JsonParser().parse(
                                response.errorBody().string()
                        );
                        Gson gson = new Gson();
                        ErrorResponse errorResponse = gson.fromJson(
                                loginJsonError, ErrorResponse.class
                        );
                        view.onLoginFailure(errorResponse);
                    } catch (IOException e) {
                        // Backend problem: API didn't return a JSON error string.
                    }
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<LoginResponse> call,
                    @NonNull Throwable t
            ) {
                view.onNetworkError();
            }
        });
    }
}
