package com.example.ChatAppV2.model;

public class AccountData extends LoginData {
    private String email;
    private String confirmationCode;
    private String username;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }
}
