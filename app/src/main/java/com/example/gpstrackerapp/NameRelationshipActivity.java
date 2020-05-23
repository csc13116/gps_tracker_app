package com.example.gpstrackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NameRelationshipActivity extends AppCompatActivity {
    EditText mParentRelationshipEditText;
    String mChildName, mParentId;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_relationship);
        mParentRelationshipEditText = (EditText) findViewById(R.id.textView_parent_relationship);
        Intent intent = getIntent();
        if (intent != null) {
            mChildName = intent.getStringExtra("childName");
            mParentId = intent.getStringExtra("parentId");
        }

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(mParentId).child("Children");
    }

    public void onConfirm(View v) {
        DatabaseReference newRef = mDatabaseReference.child(mChildName.toString()).child("relationship");
        newRef.setValue(mParentRelationshipEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Succeed to add relationship", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Fail to add relationship", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}