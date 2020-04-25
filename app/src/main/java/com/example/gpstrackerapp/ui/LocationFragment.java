package com.example.gpstrackerapp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gpstrackerapp.HomePageActivity;
import com.example.gpstrackerapp.InviteCodeActivity;
import com.example.gpstrackerapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LocationFragment extends Fragment {
    FloatingActionButton create_child_button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_location, container, false);
        create_child_button = (FloatingActionButton) fragment.findViewById(R.id.create_child_button);
        create_child_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inviteCode = new Intent(getContext(), InviteCodeActivity.class);
                startActivity(inviteCode);
            }
        });
        return fragment;
    }

}
