package com.example.find_it.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindItService {
    private static String BASE_URL;

    public static FindItAPI getAPI() {
        if (BASE_URL == null) {
            BASE_URL = "https://findit-backend-staging.herokuapp.com/";
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(FindItAPI.class);
    }

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }
}
