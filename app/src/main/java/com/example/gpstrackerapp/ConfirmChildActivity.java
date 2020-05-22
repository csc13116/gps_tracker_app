package com.example.gpstrackerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.gpstrackerapp.HomePageActivity.LAUNCH_MAP;

public class ConfirmChildActivity extends AppCompatActivity {
    EditText mChildNameEditText;
    Button mChildConfirmButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_child);

        mChildNameEditText = findViewById(R.id.editText_child_name);
        mChildConfirmButton = findViewById(R.id.button_confirm_child);

        mChildConfirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentToMap = new Intent();
                intentToMap.putExtra("CHILD_NAME", mChildNameEditText.getText().toString());

                setResult(LAUNCH_MAP, intentToMap);
                finish();
            }
        });
    }
}
