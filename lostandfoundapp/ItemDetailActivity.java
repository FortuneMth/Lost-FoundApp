package com.example.lostandfoundapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.lostandfoundapp.models.Item;
import com.example.lostandfoundapp.utils.FirebaseUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ItemDetailActivity extends AppCompatActivity {

    private Button backButton;
    private ImageView detailItemImage;
    private TextView detailItemName;
    private TextView detailItemCategory;
    private TextView detailItemStatus;
    private TextView detailItemLocation;
    private TextView detailItemDate;
    private TextView detailItemDescription;
    private TextView detailItemId;

    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        // Initialize views
        backButton = findViewById(R.id.backButton);
        detailItemImage = findViewById(R.id.detailItemImage);
        detailItemName = findViewById(R.id.detailItemName);
        detailItemCategory = findViewById(R.id.detailItemCategory);
        detailItemStatus = findViewById(R.id.detailItemStatus);
        detailItemLocation = findViewById(R.id.detailItemLocation);
        detailItemDate = findViewById(R.id.detailItemDate);
        detailItemDescription = findViewById(R.id.detailItemDescription);
        detailItemId = findViewById(R.id.detailItemId);

        // Get item ID from intent
        String itemId = getIntent().getStringExtra("item_id");

        if (itemId != null) {
            loadItemDetails(itemId);
        } else {
            Toast.makeText(this, "Item not found", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Setup back button
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void loadItemDetails(String itemId) {
        FirebaseUtils.getItemById(itemId, new FirebaseUtils.OnItemRetrievedListener() {
            @Override
            public void onSuccess(Item retrievedItem) {
                if (retrievedItem != null) {
                    item = retrievedItem;
                    displayItemDetails();
                } else {
                    Toast.makeText(ItemDetailActivity.this, "Item not found", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(ItemDetailActivity.this, "Error loading item: " + errorMessage, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void displayItemDetails() {
        if (item == null) return;

        // Set item name
        detailItemName.setText(item.getName());

        // Set category
        detailItemCategory.setText(item.getCategory() != null ? item.getCategory() : "Unknown");

        // Set status
        if (item.isLost()) {
            detailItemStatus.setText("Lost Item");
            detailItemStatus.setTextColor(getResources().getColor(android.R.color.holo_red_light));
        } else {
            detailItemStatus.setText("Found Item");
            detailItemStatus.setTextColor(getResources().getColor(android.R.color.holo_green_light));
        }

        // Set location
        detailItemLocation.setText(item.getLocation() != null ? item.getLocation() : "Not specified");

        // Set date
        if (item.getDate() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            detailItemDate.setText(dateFormat.format(item.getDate()));
        } else {
            detailItemDate.setText("Not specified");
        }

        // Set description
        detailItemDescription.setText(item.getDescription() != null ? item.getDescription() : "No description");

        // Set item ID
        detailItemId.setText(item.getId() != null ? item.getId() : "N/A");

        // Load image
        if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
            Glide.with(this)
                    .load(item.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(detailItemImage);
        } else {
            detailItemImage.setImageResource(R.drawable.ic_launcher_background);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
