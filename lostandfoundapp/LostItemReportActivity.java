package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.app.Activity;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;

public class LostItemReportActivity extends AppCompatActivity {
    
    private Button nextButton;
    private Button backButton;
    private String itemName;
    private String category;
    private String description;
    private String lostDate;
    private String lostTime;

    private FrameLayout imageUpload1;
    private FrameLayout imageUpload2;
    private FrameLayout imageUpload3;
    private FrameLayout imageUpload4;

    private Uri imageUri1;
    private Uri imageUri2;
    private Uri imageUri3;
    private Uri imageUri4;

    private static final int PICK_IMAGE_1 = 1;
    private static final int PICK_IMAGE_2 = 2;
    private static final int PICK_IMAGE_3 = 3;
    private static final int PICK_IMAGE_4 = 4;
    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_item_report);

        // Get data from previous activity
        Intent intent = getIntent();
        itemName = intent.getStringExtra("itemName");
        category = intent.getStringExtra("category");
        description = intent.getStringExtra("description");
        lostDate = intent.getStringExtra("lostDate");
        lostTime = intent.getStringExtra("lostTime");

        // Initialize views
        imageUpload1 = findViewById(R.id.imageUpload1);
        imageUpload2 = findViewById(R.id.imageUpload2);
        imageUpload3 = findViewById(R.id.imageUpload3);
        imageUpload4 = findViewById(R.id.imageUpload4);
        nextButton = findViewById(R.id.continueButton);
        backButton = findViewById(R.id.backButton);

        // Request permissions
        requestStoragePermissions();

        // Setup image upload listeners
        imageUpload1.setOnClickListener(v -> pickImage(PICK_IMAGE_1));
        imageUpload2.setOnClickListener(v -> pickImage(PICK_IMAGE_2));
        imageUpload3.setOnClickListener(v -> pickImage(PICK_IMAGE_3));
        imageUpload4.setOnClickListener(v -> pickImage(PICK_IMAGE_4));

        // Setup button listeners
        if (nextButton != null) {
            nextButton.setOnClickListener(v -> proceedToLocation());
        }

        if (backButton != null) {
            backButton.setOnClickListener(v -> onBackPressed());
        }
    }

    private void requestStoragePermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void pickImage(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                switch (requestCode) {
                    case PICK_IMAGE_1:
                        imageUri1 = selectedImage;
                        showImagePreview(imageUpload1, imageUri1, R.id.image1Preview);
                        Toast.makeText(this, "Image 1 selected", Toast.LENGTH_SHORT).show();
                        break;
                    case PICK_IMAGE_2:
                        imageUri2 = selectedImage;
                        showImagePreview(imageUpload2, imageUri2, R.id.image2Preview);
                        Toast.makeText(this, "Image 2 selected", Toast.LENGTH_SHORT).show();
                        break;
                    case PICK_IMAGE_3:
                        imageUri3 = selectedImage;
                        showImagePreview(imageUpload3, imageUri3, R.id.image3Preview);
                        Toast.makeText(this, "Image 3 selected", Toast.LENGTH_SHORT).show();
                        break;
                    case PICK_IMAGE_4:
                        imageUri4 = selectedImage;
                        showImagePreview(imageUpload4, imageUri4, R.id.image4Preview);
                        Toast.makeText(this, "Image 4 selected", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    }

    private void showImagePreview(FrameLayout container, Uri imageUri, int imageViewId) {
        ImageView imageView = container.findViewById(imageViewId);
        if (imageView != null) {
            imageView.setImageURI(imageUri);
            imageView.setVisibility(android.view.View.VISIBLE);
        }
    }

    private void proceedToLocation() {
        Intent intent = new Intent(this, LostItemLocationActivity.class);
        intent.putExtra("itemName", itemName);
        intent.putExtra("category", category);
        intent.putExtra("description", description);
        intent.putExtra("lostDate", lostDate);
        intent.putExtra("lostTime", lostTime);
        if (imageUri1 != null) intent.putExtra("imageUri1", imageUri1.toString());
        if (imageUri2 != null) intent.putExtra("imageUri2", imageUri2.toString());
        if (imageUri3 != null) intent.putExtra("imageUri3", imageUri3.toString());
        if (imageUri4 != null) intent.putExtra("imageUri4", imageUri4.toString());
        startActivity(intent);
    }
}
