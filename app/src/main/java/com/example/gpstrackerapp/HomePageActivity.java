package com.example.gpstrackerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gpstrackerapp.ui.AccountFragment;
import com.example.gpstrackerapp.ui.HistoryFragment;
import com.example.gpstrackerapp.ui.LocationFragment;
import com.example.gpstrackerapp.ui.MessageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePageActivity extends FragmentActivity {

    BottomNavigationView bottomNavigationView;
    public static final int LAUNCH_MAP = 1;
    String childId;
    String userId;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home_page);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        childId = getIntent().getStringExtra("CHILD_ID");
        userId = getIntent().getStringExtra("USER_ID");

        final Bundle bundleToFragment = new Bundle();
        bundleToFragment.putString("CHILD_ID_FOR_MAP", childId);

        final Bundle bundleToServerFragment = new Bundle();
        bundleToServerFragment.putString("USER_ID_FOR_SERVER", userId);


        if(savedInstanceState == null){
            Fragment fragment = new LocationFragment();
            fragment.setArguments(bundleToServerFragment);
            HomePageActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
        }


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_location:
                        if (getSupportFragmentManager().findFragmentByTag("HISTORY_MAP_FRAGMENT") != null)
                        {
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.detach(getSupportFragmentManager().findFragmentByTag("HISTORY_MAP_FRAGMENT"));
                        }

                        if (getSupportFragmentManager().findFragmentByTag("MAP_ACTIVITY_FRAGMENT") == null) {
                            fragment = new LocationFragment();
                            fragment.setArguments(bundleToServerFragment);
                            getSupportFragmentManager().beginTransaction().add(R.id.fragment, fragment).commit();
                            break;
                        }
                        else {
                            ParentMapActivity mapActivityFragment = (ParentMapActivity) getSupportFragmentManager().findFragmentByTag("MAP_ACTIVITY_FRAGMENT");
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.detach(mapActivityFragment);
                            ft.attach(mapActivityFragment);
                            ft.commit();

                            break;
                        }
                    case R.id.navigation_history:
                        if (getSupportFragmentManager().findFragmentByTag("MAP_ACTIVITY_FRAGMENT") != null)
                        {
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.detach(getSupportFragmentManager().findFragmentByTag("MAP_ACTIVITY_FRAGMENT"));
                        }

                        if (getSupportFragmentManager().findFragmentByTag("HISTORY_MAP_FRAGMENT") == null) {
                            HistoryMapActivity historyMapActivityFragment = new HistoryMapActivity();
                            historyMapActivityFragment.setArguments(bundleToFragment);
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.add(R.id.fragment, historyMapActivityFragment, "HISTORY_MAP_FRAGMENT");
                            ft.addToBackStack("HISTORY_MAP_FRAGMENT");
                            ft.detach(historyMapActivityFragment);
                            ft.attach(historyMapActivityFragment);
                            ft.commit();

                            getSupportFragmentManager().executePendingTransactions();
                            break;
                        }
                        else {
                            HistoryMapActivity historyMapActivityFragment = (HistoryMapActivity) getSupportFragmentManager().findFragmentByTag("HISTORY_MAP_FRAGMENT");
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.detach(historyMapActivityFragment);
                            ft.attach(historyMapActivityFragment);
                            ft.commit();

                            break;
                        }
                }

                return true;
            }
        });
    }
}


