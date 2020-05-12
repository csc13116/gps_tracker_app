package com.example.gpstrackerapp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gpstrackerapp.ConfirmChildActivity;
import com.example.gpstrackerapp.HomePageActivity;
import com.example.gpstrackerapp.InviteCodeActivity;
import com.example.gpstrackerapp.MapActivity;
import com.example.gpstrackerapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.app.Activity.RESULT_OK;
import static com.example.gpstrackerapp.HomePageActivity.LAUNCH_MAP;

public class LocationFragment extends Fragment {
    FloatingActionButton create_child_button;
    String childName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_location, container, false);
        create_child_button = (FloatingActionButton) fragment.findViewById(R.id.create_child_button);
        create_child_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inviteCode = new Intent(getContext(), InviteCodeActivity.class);
                startActivityForResult(inviteCode, LAUNCH_MAP);
            }
        });
        return fragment;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((data != null) && (requestCode == LAUNCH_MAP)) {
            childName = data.getStringExtra("CHILD_NAME");

            MapActivity mapActivityFragment = new MapActivity();
            Bundle bundleToFragment = new Bundle();
            bundleToFragment.putString("CHILD_NAME_FOR_MAP", childName);
            mapActivityFragment.setArguments(bundleToFragment);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment, mapActivityFragment, "MAP_ACTIVITY_FRAGMENT");
            ft.addToBackStack("MAP_ACTIVITY_FRAGMENT");
            ft.commit();

            getFragmentManager().executePendingTransactions();
        }
    }

}
