package com.example.gpstrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static com.example.gpstrackerapp.config.BackendConnection.AUTHENTICATION_REGISTRATION_URL;

public class RegisterActivity extends AppCompatActivity {
    EditText mEmailEditText, mPasswordEditText;
    TextView mMessageTextView;
    ProgressDialog dialog;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mEmailEditText = (EditText) findViewById(R.id.editText_Email);
        mPasswordEditText = (EditText) findViewById(R.id.editText_password);
        mMessageTextView = (TextView) findViewById(R.id.textView_message);
    }

    public void onRegister(View v) {
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        String url = AUTHENTICATION_REGISTRATION_URL;
        StringRequest register = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mMessageTextView.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.data != null) {
                    mMessageTextView.setText(new String(networkResponse.data));
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
        queue.add(register);
    }
}
