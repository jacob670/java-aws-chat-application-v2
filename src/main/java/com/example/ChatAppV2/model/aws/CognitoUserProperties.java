package com.example.ChatAppV2.model.aws;

public class CognitoUserProperties {
    private String userName;
    private String email;
    private String issuer;
    private boolean isEmailVerified;

    public CognitoUserProperties(String userName, String email, String issuer, boolean isEmailVerified) {
        this.userName = userName;
        this.email = email;
        this.issuer = issuer;
        this.isEmailVerified = isEmailVerified;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        isEmailVerified = emailVerified;
    }
}
