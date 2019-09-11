package com.example.find_it.login.model;


import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("refresh_token")
    private String refreshToken;

    public LoginResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LoginResponse) {
            LoginResponse comparedObj = (LoginResponse) obj;
            if (this.accessToken != null) {
                if (!this.accessToken.equals(comparedObj.getAccessToken())) {
                    return false;
                }
            }
            if (this.refreshToken != null) {
                return this.refreshToken.equals(comparedObj.getRefreshToken());
            }
            return false;
        } else {
            return false;
        }
    }
}
