package com.example.app_healix;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button logOut = findViewById(R.id.signOut);

        logOut.setOnClickListener(view -> {
            FirebaseAuth mAuth=FirebaseAuth.getInstance();
                mAuth.signOut();

            Intent intent=new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();


        });

    }
}