package com.example.find_it.service;

import com.example.find_it.login.model.LoginData;
import com.example.find_it.login.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FindItAPI {
    @POST("login")
    Call<LoginResponse> loginUser(@Body LoginData loginData);
}
