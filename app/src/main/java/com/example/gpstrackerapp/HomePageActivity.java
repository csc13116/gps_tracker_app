package com.example.gpstrackerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

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

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        if(savedInstanceState == null){
            HomePageActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new LocationFragment()).commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_location:
                        if (HomePageActivity.this.getFragmentManager().findFragmentByTag("MAP_ACTIVITY_FRAGMENT") == null) {
                            fragment = new MapActivity();
                            break;
                        }
                        else {
                            MapActivity mapActivityFragment = (MapActivity) HomePageActivity.this.getSupportFragmentManager().findFragmentByTag("MAP_ACTIVITY_FRAGMENT");
                            HomePageActivity.this.getSupportFragmentManager().beginTransaction().show(mapActivityFragment);
                            break;
                        }
                    case R.id.navigation_message:
                        fragment= new MessageFragment();
                        break;
                    case R.id.navigation_history:
                        fragment= new HistoryFragment();
                        break;
                    case R.id.navigation_account:
                        fragment=new AccountFragment();
                        break;
                }
                HomePageActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();

                return true;
            }
        });
    }
}


