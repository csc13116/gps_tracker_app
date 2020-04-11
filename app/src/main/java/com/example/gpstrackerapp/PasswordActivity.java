package com.example.gpstrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class PasswordActivity extends AppCompatActivity {

    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        Intent myIntent = getIntent();
        if(myIntent!=null)
        {
            email = myIntent.getStringExtra("email");
        }
    }
}
