package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lostandfoundapp.activities.LocationPickerActivity;

public class LostItemLocationActivity extends AppCompatActivity {

    private Button continueButton;
    private Button backButton;
    private Button openMapButton;
    private TextView currentLocationText;
    
    private String itemName;
    private String category;
    private String description;
    private String lostDate;
    private String lostTime;
    private String selectedLocation = "";
    
    private String[] imageUris = new String[4];
    private static final int LOCATION_PICKER_REQUEST = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_item_location);

        // Get data from previous activity
        Intent intent = getIntent();
        itemName = intent.getStringExtra("itemName");
        category = intent.getStringExtra("category");
        description = intent.getStringExtra("description");
        lostDate = intent.getStringExtra("lostDate");
        lostTime = intent.getStringExtra("lostTime");
        imageUris[0] = intent.getStringExtra("imageUri1");
        imageUris[1] = intent.getStringExtra("imageUri2");
        imageUris[2] = intent.getStringExtra("imageUri3");
        imageUris[3] = intent.getStringExtra("imageUri4");

        // Initialize views
        continueButton = findViewById(R.id.continueButton);
        backButton = findViewById(R.id.backButton);
        currentLocationText = findViewById(R.id.currentLocationText);
        openMapButton = findViewById(R.id.openMapButton);

        // Setup button listeners
        if (openMapButton != null) {
            openMapButton.setOnClickListener(v -> openLocationPicker());
        }

        if (continueButton != null) {
            continueButton.setOnClickListener(v -> proceedToReview());
        }

        if (backButton != null) {
            backButton.setOnClickListener(v -> onBackPressed());
        }
    }

    private void openLocationPicker() {
        Intent intent = new Intent(this, LocationPickerActivity.class);
        startActivityForResult(intent, LOCATION_PICKER_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == LOCATION_PICKER_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedLocation = data.getStringExtra("location_name");
            double latitude = data.getDoubleExtra("latitude", 0);
            double longitude = data.getDoubleExtra("longitude", 0);
            
            if (currentLocationText != null) {
                currentLocationText.setText("Location: " + selectedLocation);
            }
            Toast.makeText(this, "Location selected: " + selectedLocation, Toast.LENGTH_SHORT).show();
        }
    }

    private void proceedToReview() {
        if (selectedLocation.isEmpty()) {
            Toast.makeText(this, "Please select a location using the map", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, LostItemReviewActivity.class);
        intent.putExtra("itemName", itemName);
        intent.putExtra("category", category);
        intent.putExtra("description", description);
        intent.putExtra("lostDate", lostDate);
        intent.putExtra("lostTime", lostTime);
        intent.putExtra("location", selectedLocation);
        intent.putExtra("imageUri1", imageUris[0]);
        intent.putExtra("imageUri2", imageUris[1]);
        intent.putExtra("imageUri3", imageUris[2]);
        intent.putExtra("imageUri4", imageUris[3]);
        startActivity(intent);
    }
}
