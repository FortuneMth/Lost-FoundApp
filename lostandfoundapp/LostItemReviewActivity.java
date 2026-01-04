package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lostandfoundapp.models.Item;
import com.example.lostandfoundapp.utils.FirebaseUtils;
import android.net.Uri;
import java.util.Date;

public class LostItemReviewActivity extends AppCompatActivity {
    private static final String TAG = "LostItemReview";

    private Button submitButton;
    private Button cancelButton;
    private ProgressBar progressBar;

    private TextView reviewItemName;
    private TextView reviewCategory;
    private TextView reviewDescription;
    private TextView reviewDate;
    private TextView reviewTime;
    private TextView reviewLocation;

    private ImageView reviewImage1;
    private ImageView reviewImage2;
    private ImageView reviewImage3;
    private ImageView reviewImage4;

    private String itemName;
    private String category;
    private String description;
    private String lostDate;
    private String lostTime;
    private String location;
    private String[] imageUris = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_item_review);

        // Get data from previous activity
        Intent intent = getIntent();
        itemName = intent.getStringExtra("itemName");
        category = intent.getStringExtra("category");
        description = intent.getStringExtra("description");
        lostDate = intent.getStringExtra("lostDate");
        lostTime = intent.getStringExtra("lostTime");
        location = intent.getStringExtra("location");
        imageUris[0] = intent.getStringExtra("imageUri1");
        imageUris[1] = intent.getStringExtra("imageUri2");
        imageUris[2] = intent.getStringExtra("imageUri3");
        imageUris[3] = intent.getStringExtra("imageUri4");

        // Initialize views
        initializeViews();

        // Display data
        displayReviewData();

        // Setup button listeners
        submitButton.setOnClickListener(v -> {
            Log.d(TAG, "Submit button clicked!");
            submitReport();
        });
        cancelButton.setOnClickListener(v -> {
            Log.d(TAG, "Cancel button clicked");
            onBackPressed();
        });
    }

    private void initializeViews() {
        reviewItemName = findViewById(R.id.reviewItemName);
        reviewCategory = findViewById(R.id.reviewCategory);
        reviewDescription = findViewById(R.id.reviewDescription);
        reviewDate = findViewById(R.id.reviewDate);
        reviewTime = findViewById(R.id.reviewTime);
        reviewLocation = findViewById(R.id.reviewLocation);

        reviewImage1 = findViewById(R.id.reviewImage1);
        reviewImage2 = findViewById(R.id.reviewImage2);
        reviewImage3 = findViewById(R.id.reviewImage3);
        reviewImage4 = findViewById(R.id.reviewImage4);

        submitButton = findViewById(R.id.submitButton);
        cancelButton = findViewById(R.id.cancelButton);
        progressBar = findViewById(R.id.progressBar);
        if (progressBar != null) {
            progressBar.setVisibility(android.view.View.GONE);
        }
    }

    private void displayReviewData() {
        // Display item info
        reviewItemName.setText(itemName != null ? itemName : "N/A");
        reviewCategory.setText(category != null ? category : "Other");
        reviewDescription.setText(description != null ? description : "N/A");

        // Display date and time
        reviewDate.setText(lostDate != null ? lostDate : "N/A");
        reviewTime.setText(lostTime != null ? lostTime : "N/A");

        // Display location
        reviewLocation.setText(location != null ? location : "N/A");

        // Display images
        displayImages();
    }

    private void displayImages() {
        ImageView[] imageViews = {reviewImage1, reviewImage2, reviewImage3, reviewImage4};

        for (int i = 0; i < 4; i++) {
            if (imageUris[i] != null && !imageUris[i].isEmpty()) {
                imageViews[i].setImageURI(Uri.parse(imageUris[i]));
            }
        }
    }

    private void submitReport() {
        Log.d(TAG, "submitReport() called");
        Log.d(TAG, "Item Name: " + itemName);
        Log.d(TAG, "Location: " + location);
        Log.d(TAG, "Category: " + category);
        
        if (progressBar != null) {
            Log.d(TAG, "Showing progress bar");
            progressBar.setVisibility(android.view.View.VISIBLE);
        } else {
            Log.e(TAG, "ERROR: progressBar is null!");
        }
        
        submitButton.setEnabled(false);
        cancelButton.setEnabled(false);

        // Create Item object
        Item item = new Item(
            itemName,
            description,
            category,
            location,
            new Date(),
            "", // imageUrl - will be set after upload
            "", // userId - will be set by Firebase
            true // isLost
        );
        
        Log.d(TAG, "Item object created, attempting to save to Firebase");

        // Save to Firebase
        FirebaseUtils.saveItem(item, new FirebaseUtils.OnItemSaveListener() {
            @Override
            public void onSuccess(String itemId) {
                Log.d(TAG, "Firebase onSuccess called with itemId: " + itemId);
                if (progressBar != null) {
                    progressBar.setVisibility(android.view.View.GONE);
                }
                Toast.makeText(LostItemReviewActivity.this, 
                    "Lost item reported successfully!", Toast.LENGTH_SHORT).show();
                
                Log.d(TAG, "Starting ReportSuccessActivity");
                // Navigate to success screen
                Intent intent = new Intent(LostItemReviewActivity.this, ReportSuccessActivity.class);
                intent.putExtra("itemId", itemId);
                intent.putExtra("itemType", "lost");
                intent.putExtra("itemName", itemName);
                Log.d(TAG, "Intent extras added, starting activity");
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e(TAG, "Firebase onFailure called: " + errorMessage);
                if (progressBar != null) {
                    progressBar.setVisibility(android.view.View.GONE);
                }
                submitButton.setEnabled(true);
                cancelButton.setEnabled(true);
                Toast.makeText(LostItemReviewActivity.this, 
                    "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
