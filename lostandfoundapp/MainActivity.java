package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.example.lostandfoundapp.fragments.HomeFragment;
import com.example.lostandfoundapp.fragments.MatchesFragment;
import com.example.lostandfoundapp.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        // Check if user is logged in
        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // Initialize views
        bottomNavigation = findViewById(R.id.bottomNavigation);

        // Setup bottom navigation
        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_matches) {
                selectedFragment = new MatchesFragment();
            } else if (itemId == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, selectedFragment)
                        .commit();
                return true;
            }
            return false;
        });

        // Set default fragment and check for intent extras
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            String tabToSelect = intent.getStringExtra("tab");
            
            if ("matches".equals(tabToSelect)) {
                bottomNavigation.setSelectedItemId(R.id.nav_matches);
            } else {
                bottomNavigation.setSelectedItemId(R.id.nav_home);
            }
        }
    }
}
