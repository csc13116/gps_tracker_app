package com.example.gpstrackerapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
                String childName = etNameChild.getText().toString();
                //Intent intent = new Intent(ConfirmChildActivity.this, HomePageActivity.class);
                //intent.putExtra("LOAD_MENU_ITEM", "loadMapFragment");
                //Intent returnIntent = new Intent();

                Bundle bundle = new Bundle();
                bundle.putString("CHILD_NAME", childName);
                Fragment mapActivity = new MapActivity();
                mapActivity.setArguments(bundle);

                setResult(LAUNCH_MAP);
                finish();
                //intent.putExtra("CHILD_NAME", childName);
                //startActivity(intent);
            }
        });

    }
}
