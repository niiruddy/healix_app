package com.example.app_healix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class dashboard extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nursedashboard);


        bottomNav = findViewById(R.id.bottom_nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new NurseDash()).commit();
        bottomNav.setSelectedItemId(R.id.nav_home);


        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem Item) {

                Fragment fragment = null;


                switch (Item.getItemId()) {
                    case R.id.nav_home:
                        fragment = new NurseDash();
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


    // this is where we get the current location of the user
//    private void getCurrentLocation() {
//
//        mlocationRequest = new LocationRequest();
//        mlocationRequest.setInterval(1000);
//        mlocationRequest.setFastestInterval(1000);
//        mlocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            return;
//
//        }
//        client.requestLocationUpdates(mlocationRequest, callback, Looper.myLooper());
//
//        //initialize task location
//        Task<Location> task = client.getLastLocation();
//
//
//        task.addOnSuccessListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//
//                FirebaseDatabase ref = FirebaseDatabase.getInstance();
//                DatabaseReference loc = ref.getReference("Caregiver_loc");
//                loc.setValue(location);
//
//
//            }
//        });
//
//
//    }


}