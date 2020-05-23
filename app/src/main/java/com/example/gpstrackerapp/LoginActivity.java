package com.example.gpstrackerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static com.example.gpstrackerapp.config.BackendConnection.AUTHENTICATION_LOGIN_URL;

public class LoginActivity extends AppCompatActivity {

    EditText mEmailEditText, mPasswordEditText;
    TextView mMessageTextView;
    ProgressDialog dialog;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailEditText = findViewById(R.id.editText_Email);
        mPasswordEditText = findViewById(R.id.editText_password);
        mMessageTextView = findViewById(R.id.textView_message);
    }

    public void onLogin(View view) {
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        String url = AUTHENTICATION_LOGIN_URL;
        StringRequest login = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Intent homepage = new Intent(LoginActivity.this, HomePageActivity.class);
                startActivity(homepage);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.data != null) {
                    String jsonError = new String(networkResponse.data);
                    mMessageTextView.setText(jsonError);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", mEmailEditText.getText().toString().split("@")[0]);
                params.put("password", mPasswordEditText.getText().toString());
                return params;
            }
        };
        queue.add(login);
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
