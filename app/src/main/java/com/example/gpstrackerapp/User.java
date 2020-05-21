package com.example.gpstrackerapp;

public class User {
    public User() {
    }

    public User(String email, String password, String code, String id) {
        this.email = email;
        this.password = password;
        this.code = code;
        this.id = id;
    }

    public String email;
    public String password;
    public String code;
    public String id;
}
