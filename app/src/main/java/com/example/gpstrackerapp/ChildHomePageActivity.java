package com.example.gpstrackerapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.gpstrackerapp.ui.AccountFragment;
import com.example.gpstrackerapp.ui.HistoryFragment;
import com.example.gpstrackerapp.ui.LocationFragment;
import com.example.gpstrackerapp.ui.MessageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ChildHomePageActivity extends FragmentActivity {

    BottomNavigationView bottomNavigationView;
    public static final int LAUNCH_MAP = 1;
    String childId;
    String phoneNumber;

    MapActivity existedMapActivityFragment;
    EmergencyActivity existedEmergencyActivity;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        childId = getIntent().getStringExtra("CHILD_ID");
        phoneNumber = getIntent().getStringExtra("PHONE_NUMBER");
        final Bundle bundleToFragment = new Bundle();
        bundleToFragment.putString("CHILD_ID_FOR_MAP", childId);
        bundleToFragment.putString("PHONE_NUMBER_FOR_EMERGENCY", phoneNumber);

        if(savedInstanceState == null){
            MapActivity mapActivityFragment = new MapActivity();
            mapActivityFragment.setArguments(bundleToFragment);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragment, mapActivityFragment);
            ft.commit();

            getSupportFragmentManager().executePendingTransactions();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_location:
                        if (getSupportFragmentManager().findFragmentByTag("MAP_ACTIVITY_FRAGMENT") == null) {
                            MapActivity mapActivityFragment = new MapActivity();
                            mapActivityFragment.setArguments(bundleToFragment);
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.add(R.id.fragment, mapActivityFragment, "MAP_ACTIVITY_FRAGMENT");
                            ft.addToBackStack("MAP_ACTIVITY_FRAGMENT");
                            existedMapActivityFragment = (MapActivity) getSupportFragmentManager().findFragmentByTag("MAP_ACTIVITY_FRAGMENT");
                            ft.detach(mapActivityFragment);
                            ft.attach(mapActivityFragment);
                            ft.commit();

                            getSupportFragmentManager().executePendingTransactions();
                            break;
                        }
                        else {
                            if (getSupportFragmentManager().findFragmentByTag("EMERGENCY_FRAGMENT") != null)
                            {
                                existedEmergencyActivity = (EmergencyActivity) getSupportFragmentManager().findFragmentByTag("EMERGENCY_FRAGMENT");
                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                getSupportFragmentManager().beginTransaction().hide(existedEmergencyActivity);
                            }

                            existedMapActivityFragment = (MapActivity) getSupportFragmentManager().findFragmentByTag("MAP_ACTIVITY_FRAGMENT");
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.detach(existedMapActivityFragment);
                            ft.attach(existedMapActivityFragment);
                            ft.commit();

                            break;
                        }
                    case R.id.navigation_message:
                        if (getSupportFragmentManager().findFragmentByTag("EMERGENCY_FRAGMENT") == null) {

                            if (getSupportFragmentManager().findFragmentByTag("MAP_ACTIVITY_FRAGMENT") != null)
                            {
                                existedMapActivityFragment = (MapActivity) getSupportFragmentManager().findFragmentByTag("MAP_ACTIVITY_FRAGMENT");
                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                getSupportFragmentManager().beginTransaction().hide(existedMapActivityFragment);
                            }

                            EmergencyActivity emergencyActivity = new EmergencyActivity();
                            emergencyActivity.setArguments(bundleToFragment);
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.add(R.id.fragment, emergencyActivity, "EMERGENCY_FRAGMENT");
                            ft.addToBackStack("EMERGENCY_FRAGMENT");
                            existedEmergencyActivity = (EmergencyActivity) getSupportFragmentManager().findFragmentByTag("EMERGENCY_FRAGMENT");
                            ft.detach(emergencyActivity);
                            ft.attach(emergencyActivity);
                            ft.commit();

                            getSupportFragmentManager().executePendingTransactions();
                            break;
                        }
                        else {
                            existedEmergencyActivity = (EmergencyActivity) getSupportFragmentManager().findFragmentByTag("EMERGENCY_FRAGMENT");
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.detach(existedEmergencyActivity);
                            ft.attach(existedEmergencyActivity);
                            ft.commit();

                            break;
                        }
                    case R.id.navigation_history:
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
                    case R.id.navigation_account:
                        fragment=new AccountFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.fragment, fragment).commit();
                        break;
                }

                return true;
            }
        });
    }
}


