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

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        childId = getIntent().getStringExtra("CHILD_ID");
        final Bundle bundleToFragment = new Bundle();
        bundleToFragment.putString("CHILD_ID_FOR_MAP", childId);

        if(savedInstanceState == null){
            HomePageActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new LocationFragment()).commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_location:
                        if (getSupportFragmentManager().findFragmentByTag("MAP_ACTIVITY_FRAGMENT") == null) {
                            fragment = new LocationFragment();
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
                    case R.id.navigation_message:
                        fragment= new MessageFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.fragment, fragment).commit();
                        break;
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


