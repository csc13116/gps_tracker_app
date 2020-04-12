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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    EditText email, password;
    FirebaseAuth auth;
    ProgressDialog dialog;
    String code;
    FirebaseUser user;
    DatabaseReference reference;

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

        reference = FirebaseDatabase.getInstance().getReference().child("Users");

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

                        int n = 10000 + r.nextInt(89999);
                        code = String.valueOf(n);

                        createNewUser();
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

    public void createNewUser() {
        auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    CreateUser newUser = new CreateUser(email.getText().toString(), password.getText().toString(), code);

                    user = auth.getCurrentUser();
                    String userId = user.getUid();
                    DatabaseReference newRef = reference.push();
                    newRef.setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //   dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "User registered successfully", Toast.LENGTH_LONG).show();
                            } else {
                                //   dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "User register failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public void goToLoginActivity(View view) {
        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(loginIntent);

    }
}
