package com.example.gpstrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToRegisterActivity(View v){
        Intent mainIntent = new Intent(MainActivity.this, RegisterLoginActivity.class);
        startActivity(mainIntent);
    }

    public void goToEnterCode(View v){
        Intent mainIntent = new Intent(MainActivity.this, JoinCircleActivity.class);
        startActivity(mainIntent);
    }
}
