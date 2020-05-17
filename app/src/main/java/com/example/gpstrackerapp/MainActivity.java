package com.example.gpstrackerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import static com.example.gpstrackerapp.HomePageActivity.LAUNCH_MAP;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToRegisterActivity(View v){
        Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(mainIntent);
    }

    public void goToEnterCode(View v){
        Intent mainIntent = new Intent(MainActivity.this, EnterCodeActivity.class);
        startActivity(mainIntent);
    }
}
