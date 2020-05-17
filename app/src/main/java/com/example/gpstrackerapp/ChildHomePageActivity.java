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

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        if(savedInstanceState == null){
            MapActivity mapActivityFragment = new MapActivity();
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
                        {
                            MapActivity mapActivityFragment = new MapActivity();
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.add(R.id.fragment, mapActivityFragment);
                            ft.detach(mapActivityFragment);
                            ft.attach(mapActivityFragment);
                            ft.commit();

                            getSupportFragmentManager().executePendingTransactions();
                            break;
                        }
                    case R.id.navigation_message:
                        fragment= new MessageFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.fragment, fragment).commit();
                        break;
                    case R.id.navigation_history:
                        fragment= new HistoryFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.fragment, fragment).commit();
                        break;
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


