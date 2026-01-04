package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ReportSuccessActivity extends AppCompatActivity {
    
    private Button goHomeButton;
    private TextView successTitle;
    private TextView successMessage;
    private TextView itemIdText;
    private TextView infoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_success);

        // Initialize views
        successTitle = findViewById(R.id.successTitle);
        successMessage = findViewById(R.id.successMessage);
        itemIdText = findViewById(R.id.itemIdText);
        infoText = findViewById(R.id.infoText);
        goHomeButton = findViewById(R.id.goHomeButton);

        // Get item details from intent
        String itemId = getIntent().getStringExtra("itemId");
        String itemType = getIntent().getStringExtra("itemType"); // "lost" or "found"
        String itemName = getIntent().getStringExtra("itemName");

        // Set title based on item type
        if ("lost".equalsIgnoreCase(itemType)) {
            successTitle.setText("Lost Item Reported Successfully! 🔍");
        } else {
            successTitle.setText("Found Item Reported Successfully! ✅");
        }

        // Set success message
        successMessage.setText("Your item has been successfully added to our database!");

        // Set item ID
        if (itemId != null) {
            itemIdText.setText("Item ID: " + itemId);
            itemIdText.setVisibility(android.view.View.VISIBLE);
        } else {
            itemIdText.setVisibility(android.view.View.GONE);
        }

        // Set info text based on type
        if ("lost".equalsIgnoreCase(itemType)) {
            infoText.setText("We're actively searching for your lost item. You'll be notified when we find a match!");
        } else {
            infoText.setText("Thank you for reporting this found item. Someone might be looking for it!");
        }

        // Setup button listener
        goHomeButton.setOnClickListener(v -> goHome());
    }

    private void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goHome();
    }
}
