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

public class RegisterActivity extends AppCompatActivity {

    EditText email;
    FirebaseAuth auth;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = (EditText)findViewById(R.id.txt_email);
        auth = FirebaseAuth.getInstance();
    }

    public void goToPasswordActivity(View v){
        dialog.setMessage("Checking email address.");
        dialog.show();
        auth.fetchSignInMethodsForEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if(task.isSuccessful()) {
                    dialog.dismiss();
                    boolean check = !task.getResult().getSignInMethods().isEmpty();
                    if(!check){
                        // email has not existed yet
                        Intent myIntent = new Intent(RegisterActivity.this, PasswordActivity.class);
                        myIntent.putExtra("email", email.getText().toString());
                        startActivity(myIntent);
                    }else {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "This email is already registered", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }
}
