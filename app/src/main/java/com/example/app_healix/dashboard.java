package com.example.app_healix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class dashboard extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_nurse_dashboard);


        bottomNav = findViewById(R.id.bottom_nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new Nurse_Dashboard()).commit();
        bottomNav.setSelectedItemId(R.id.nav_home);


        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem Item) {

                Fragment fragment = null;


                switch (Item.getItemId()) {
                    case R.id.nav_home:
                        fragment = new Nurse_Dashboard();
                        break;

                    case R.id.nav_schedule:
                        fragment = new ScheduleFragment();
                        break;

                    case R.id.nav_chat:
                        fragment = new ChatFragment();
                        break;

                    case R.id.nav_profile:
                        fragment = new ProfileFragment();
                        break;

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();


                return true;
            }


        });


    }


}