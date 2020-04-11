package com.example.gpstrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class InviteCodeActivity extends AppCompatActivity {

    String email, password, code;
    TextView tvCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code);

        tvCode = (TextView)findViewById(R.id.textView_code) ;
        Intent registerIntent = getIntent();
        if (registerIntent != null) {
            email = registerIntent.getStringExtra("email");
            password = registerIntent.getStringExtra("password");
            code = registerIntent.getStringExtra("code");
            tvCode.setText(code);
        }
    }
}
