package com.example.gpstrackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText)findViewById(R.id.txt_email);
        password = (EditText)findViewById(R.id.txt_password);
        auth = FirebaseAuth.getInstance();
    }

    public void Login(View v){
        auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                    Toast.makeText(getApplicationContext(),"User login successful!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(),"User login failed!", Toast.LENGTH_LONG).show();

            }
        })
    }
}
