package com.example.gpstrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    TextView message;
    ProgressDialog dialog;
    String code;
//    FirebaseUser user;
//    DatabaseReference reference;
//    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.txt_email);
        password = (EditText) findViewById(R.id.txt_password);
        message = (TextView) findViewById(R.id.txtV_msg);
//      auth = FirebaseAuth.getInstance();

    }

//    public void onRegister(View v) {
//        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
//        String url = "https://dacnpm-backend.herokuapp.com/auth/register";
//        StringRequest register = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(LoginActivity.this, "User register failed", Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("username", email.getText().toString().split("@")[0]);
//                params.put("password", password.getText().toString());
//                return params;
//            }
//        };
//        queue.add(register);
//    }

//    public void generateCode(View v) {
//        dialog = new ProgressDialog(RegisterActivity.this);
//        dialog.setMessage("Checking email address...");
//        dialog.setIndeterminate(false);
//        dialog.setCancelable(true);
//        dialog.show();
//
//        reference = FirebaseDatabase.getInstance().getReference().child("Users");
//
//        auth.fetchSignInMethodsForEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
//            @Override
//            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
//                if (task.isSuccessful()) {
//                    dialog.dismiss();
//                    boolean check = !task.getResult().getSignInMethods().isEmpty();
//                    if (!check) {
//                        // email has not existed yet
//                        Date date = new Date();
//                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault());
//                        String newDate = format.format(date);
//                        Random r = new Random();
//
//                        int n = 10000 + r.nextInt(89999);
//                        code = String.valueOf(n);
//
//                        createNewUser();
//                        Intent registerIntent = new Intent(RegisterActivity.this, InviteCodeActivity.class);
//                        registerIntent.putExtra("email", email.getText().toString());
//                        registerIntent.putExtra("password", password.getText().toString());
//                        registerIntent.putExtra("code", code);
//                        startActivity(registerIntent);
//                    } else {
//                        dialog.dismiss();
//                        Toast.makeText(getApplicationContext(), "This email is already registered", Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//        });
//    }
//
//    public void createNewUser() {
//        auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    user = auth.getCurrentUser();
//                    String userId = user.getUid();
//                    User newUser = new User(email.getText().toString(), password.getText().toString(), code, userId);
//
//                    //DatabaseReference newRef = reference.push();
////                    DatabaseReference newRef = reference.child(userId).push();
//                    DatabaseReference newRef = reference.child(userId);
//                    newRef.setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(getApplicationContext(), "User registered successfully", Toast.LENGTH_LONG).show();
//                            } else {
//                                Toast.makeText(getApplicationContext(), "User register failed", Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    });
//                }
//            }
//        });
//    }

    public void onLogin(View view) {

        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        String url = "https://dacnpm-backend.herokuapp.com/auth/login";
        StringRequest login = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                JSONObject res = null;
//                try {
//                    res = new JSONObject(response);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                String msg = res.optString("mesage");
//                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.data != null) {
                    String jsonError = new String(networkResponse.data);
                    message.setText(jsonError);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", email.getText().toString().split("@")[0]);
                params.put("password", password.getText().toString());
                return params;
            }
        };
        queue.add(login);

//        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
//        loginIntent.putExtra("email", email.getText().toString());
//        loginIntent.putExtra("password", password.getText().toString());
//        startActivity(loginIntent);

    }

    public void goToRegister(View view){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
