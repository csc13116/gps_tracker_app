package com.example.gpstrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class InviteCodeActivity extends AppCompatActivity {

    String email;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code);

        password = (EditText)findViewById(R.id.txt_password);
        Intent registerIntent = getIntent();
        if (registerIntent != null) {
            email = registerIntent.getStringExtra("email");
        }
    }

    public void goToNameActivity(View v) {
        Intent passwordIntent = new Intent(InviteCodeActivity.this, NameActivity.class);
        passwordIntent.putExtra("email", email);
        passwordIntent.putExtra("password", password.getText().toString());
        startActivity(passwordIntent);
    }
}
