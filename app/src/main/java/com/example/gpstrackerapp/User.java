package com.example.gpstrackerapp;

public class User {
    public String mEmail;
    public String mPassword;
    public String mCode;
    public String mId;

    public User() {
    }

    public User(String email, String password, String code, String id) {
        this.mEmail = email;
        this.mPassword = password;
        this.mCode = code;
        this.mId = id;
    }
}
