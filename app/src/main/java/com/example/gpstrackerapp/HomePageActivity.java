package com.example.gpstrackerapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.gpstrackerapp.ui.AccountFragment;
import com.example.gpstrackerapp.ui.HistoryFragment;
import com.example.gpstrackerapp.ui.LocationFragment;
import com.example.gpstrackerapp.ui.MessageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePageActivity extends FragmentActivity {
    BottomNavigationView mBottomNavigationView;
    public static final int LAUNCH_MAP = 1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mBottomNavigationView = findViewById(R.id.bottom_navigation);

        if (savedInstanceState == null) {
            HomePageActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new LocationFragment()).commit();
        }

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_location:
                        if (getSupportFragmentManager().findFragmentByTag("MAP_ACTIVITY_FRAGMENT") == null) {
                            fragment = new LocationFragment();
                            getSupportFragmentManager().beginTransaction().add(R.id.fragment, fragment).commit();
                            break;
                        } else {
                            MapActivity mapActivityFragment = (MapActivity) getSupportFragmentManager().findFragmentByTag("MAP_ACTIVITY_FRAGMENT");
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.detach(mapActivityFragment);
                            ft.attach(mapActivityFragment);
                            ft.commit();

                            break;
                        }
                    case R.id.navigation_message:
                        fragment = new MessageFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.fragment, fragment).commit();
                        break;
                    case R.id.navigation_history:
                        fragment = new HistoryFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.fragment, fragment).commit();
                        break;
                    case R.id.navigation_account:
                        fragment = new AccountFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.fragment, fragment).commit();
                        break;
                }

                return true;
            }
        });
    }
}


