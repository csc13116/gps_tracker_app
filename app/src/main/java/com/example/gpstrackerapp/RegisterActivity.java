package com.example.gpstrackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    EditText email, password;
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = (EditText) findViewById(R.id.txt_email);
        password = (EditText) findViewById(R.id.txt_password);
        auth = FirebaseAuth.getInstance();
    }

    public void generateCode(View v) {
        dialog = new ProgressDialog(RegisterActivity.this);
        dialog.setMessage("Checking email address...");
        dialog.setIndeterminate(false);
        dialog.setCancelable(true);
        dialog.show();

        auth.fetchSignInMethodsForEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    boolean check = !task.getResult().getSignInMethods().isEmpty();
                    if (!check) {
                        // email has not existed yet
                        Date date = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault());
                        String newDate = format.format(date);
                        Random r = new Random();

                        int n = 10000 + r.nextInt(99999);
                        String code = String.valueOf(n);

                        Intent registerIntent = new Intent(RegisterActivity.this, InviteCodeActivity.class);
                        registerIntent.putExtra("email", email.getText().toString());
                        registerIntent.putExtra("password", password.getText().toString());
                        registerIntent.putExtra("code", code);
                        startActivity(registerIntent);
                    } else {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "This email is already registered", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }

    public void goToLoginActivity(View view) {
        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(loginIntent);

    }
}
