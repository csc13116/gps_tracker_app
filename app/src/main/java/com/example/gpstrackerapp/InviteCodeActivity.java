package com.example.gpstrackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InviteCodeActivity extends AppCompatActivity {

    String email, password, code;
    TextView tvCode;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;
    //ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code);

        tvCode = (TextView) findViewById(R.id.textView_code);
        auth = FirebaseAuth.getInstance();
        // reference = FirebaseDatabase.getInstance().getReference().child("Users");

        Intent registerIntent = getIntent();
        if (registerIntent != null) {
            email = registerIntent.getStringExtra("email");
            password = registerIntent.getStringExtra("password");
            code = registerIntent.getStringExtra("code");
            tvCode.setText(code);
        }
    }
}

