package com.example.gpstrackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.zip.InflaterOutputStream;

public class NameRelationshipActivity extends AppCompatActivity {
    EditText parentRelationship;
    String childName, parentId;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_relationship);
        parentRelationship = (EditText) findViewById(R.id.txt_parentRelationship);
        Intent intent = getIntent();
        if (intent != null) {
            childName = intent.getStringExtra("childName");
            parentId = intent.getStringExtra("parentId");
        }

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(parentId).child("Children");
    }

    public void onConfirm(View v) {
        DatabaseReference newRef = reference.child(childName.toString()).child("relationship");
        newRef.setValue(parentRelationship.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Succeed to add relationship", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Fail to add relationship", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        Query childQuery = reference.orderByChild("name").equalTo(childName.toString()).limitToFirst(1);
//
//        childQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    DatabaseReference newRef = reference.child("Children").child(childName.toString()).child("relationship");
//                    newRef.setValue(parentRelationship).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if(task.isSuccessful()){
//                                Toast.makeText(NameRelationshipActivity.this, "Succeed to add relationship", Toast.LENGTH_SHORT).show();
//                            }else{
//                                Toast.makeText(NameRelationshipActivity.this, "Fail to add relationship", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                    for(DataSnapshot child : dataSnapshot.getChildren()){
//                        newRef.setValue(parentRelationship).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful()){
//                                    Toast.makeText(getApplicationContext(), "Relationship added.", Toast.LENGTH_LONG).show();
//                                }else{
//                                    Toast.makeText(getApplicationContext(), "Fail to add Relationship.", Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        });
//                    }
    }

}