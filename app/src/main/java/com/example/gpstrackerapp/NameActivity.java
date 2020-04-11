package com.example.gpstrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class NameActivity extends AppCompatActivity {

    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        Intent passwordIntent = getIntent();
        if (passwordIntent != null) {
            email = passwordIntent.getStringExtra("email");
            password = passwordIntent.getStringExtra("password");
        }
    }
}
