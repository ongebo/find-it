package com.example.find_it.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindItService {
    private static final String BASE_URL = "https://findit-backend-staging.herokuapp.com/";

    public static FindItAPI getAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(FindItAPI.class);
    }
}
