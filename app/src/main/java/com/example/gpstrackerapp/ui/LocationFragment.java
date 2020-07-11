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
import com.example.gpstrackerapp.HistoryMapActivity;
import com.example.gpstrackerapp.HomePageActivity;
import com.example.gpstrackerapp.InviteCodeActivity;
import com.example.gpstrackerapp.MapActivity;
import com.example.gpstrackerapp.ParentMapActivity;
import com.example.gpstrackerapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.app.Activity.RESULT_OK;
import static com.example.gpstrackerapp.HomePageActivity.LAUNCH_MAP;

public class LocationFragment extends Fragment {
    FloatingActionButton create_child_button;
    String childName;
    String childId;
    String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_location, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null)
        {
            userId = getArguments().getString("USER_ID_FOR_SERVER");
        }

        create_child_button = (FloatingActionButton) fragment.findViewById(R.id.create_child_button);
        create_child_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inviteCode = new Intent(getContext(), InviteCodeActivity.class);
                inviteCode.putExtra("USER_ID_FOR_SERVER", userId);
                startActivityForResult(inviteCode, LAUNCH_MAP);
            }
        });
        return fragment;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((data != null) && (requestCode == LAUNCH_MAP)) {
            childName = data.getStringExtra("CHILD_NAME");
            childId = data.getStringExtra("CHILD_ID");
            getActivity().getIntent().putExtra("CHILD_ID", childId);

            ParentMapActivity mapActivityFragment = new ParentMapActivity();
            HistoryMapActivity historyMapActivityFragment = new HistoryMapActivity();
            Bundle bundleToFragment = new Bundle();
            bundleToFragment.putString("CHILD_NAME_FOR_MAP", childName);
            bundleToFragment.putString("CHILD_ID_FOR_MAP", childId);
            mapActivityFragment.setArguments(bundleToFragment);
            historyMapActivityFragment.setArguments(bundleToFragment);

            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragment, mapActivityFragment, "MAP_ACTIVITY_FRAGMENT");
            ft.add(R.id.fragment, historyMapActivityFragment, "HISTORY_MAP_FRAGMENT");
            ft.detach(historyMapActivityFragment);
            ft.addToBackStack("MAP_ACTIVITY_FRAGMENT");
            ft.addToBackStack("HISTORY_MAP_FRAGMENT");
            ft.commit();

            getActivity().getSupportFragmentManager().executePendingTransactions();
        }
    }

}
