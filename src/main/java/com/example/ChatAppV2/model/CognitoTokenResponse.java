package com.example.ChatAppV2.model;

public class CognitoTokenResponse {
    private String accessToken;
    private String idToken;
    private String refreshToken;
    private Integer expiresIn;

    public CognitoTokenResponse(String accessToken, String idToken, String refreshToken, Integer expiresIn) {
        this.accessToken = accessToken;
        this.idToken = idToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }
}
