package com.example.gpstrackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
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

public class EnterCodeActivity extends AppCompatActivity {

    Pinview code;
    // EditText childName;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code);
        code = (Pinview) findViewById(R.id.pinView_code);
        // childName = (EditText) findViewById(R.id.txt_childName);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    public void onConnect(View v) {
        Query query = reference.orderByChild("code").equalTo(code.getValue());


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User userResult = null;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        userResult = child.getValue(User.class);
                        final DatabaseReference children = reference.child(userResult.id).child("Children");
                        // Child newChild = new Child(childName.getText().toString());

                        // DatabaseReference newRef = children.child(childName.getText().toString());

                        final String parentId = userResult.id;
//                        newRef.setValue(newChild).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    Toast.makeText(getApplicationContext(), "Connect succeeded", Toast.LENGTH_LONG).show();
//                                    Intent nameRelationship = new Intent(EnterCodeActivity.this, NameRelationshipActivity.class);
//                                    nameRelationship.putExtra("childName", childName.getText().toString());
//                                    nameRelationship.putExtra("parentId", parentId);
//                                    startActivity(nameRelationship);
//                                    finish();
//                                } else {
//                                    Toast.makeText(getApplicationContext(), "Fail to connect", Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        });
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Fail to connect", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }


        });
    }
}
