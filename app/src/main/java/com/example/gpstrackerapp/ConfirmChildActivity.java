package com.example.gpstrackerapp;
import android.content.Intent;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.gpstrackerapp.ui.LocationFragment;

import java.util.Map;

import static com.example.gpstrackerapp.HomePageActivity.LAUNCH_MAP;
import static java.security.AccessController.getContext;

public class ConfirmChildActivity extends AppCompatActivity {

    EditText etNameChild;
    Button btnChildConfirm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_child);

        etNameChild =  findViewById(R.id.et_childName);
        btnChildConfirm = findViewById(R.id.btn_confirmChild);

        btnChildConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentToMap = new Intent();
                intentToMap.putExtra("CHILD_NAME", etNameChild.getText().toString());

                setResult(LAUNCH_MAP, intentToMap);
                finish();
            }
        });

    }
}
