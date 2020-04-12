package com.example.gpstrackerapp;

public class CreateUser {
    public CreateUser() {
    }

    ;

    public CreateUser(String email, String password, String code) {
        this.email = email;
        this.password = password;
        this.code = code;
    }

    public String email;
    public String password;
    public String code;

}
