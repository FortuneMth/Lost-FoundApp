package com.example.lostandfoundapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class EditProfileActivity extends AppCompatActivity {

    private EditText fullNameInput;
    private EditText phoneInput;
    private EditText emailInput;
    private Button saveButton;
    private Button cancelButton;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        initializeViews();
        loadCurrentData();
        setupListeners();
    }

    private void initializeViews() {
        fullNameInput = findViewById(R.id.fullNameInput);
        phoneInput = findViewById(R.id.phoneInput);
        emailInput = findViewById(R.id.emailInput);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
    }

    private void loadCurrentData() {
        if (currentUser != null) {
            if (currentUser.getDisplayName() != null) {
                fullNameInput.setText(currentUser.getDisplayName());
            }
            if (currentUser.getEmail() != null) {
                emailInput.setText(currentUser.getEmail());
            }
            // TODO: Load phone from Firestore
        }
    }

    private void setupListeners() {
        if (saveButton != null) {
            saveButton.setOnClickListener(v -> saveProfile());
        }

        if (cancelButton != null) {
            cancelButton.setOnClickListener(v -> onBackPressed());
        }
    }

    private void saveProfile() {
        String fullName = fullNameInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();

        // Validation
        if (fullName.isEmpty()) {
            fullNameInput.setError("Full name is required");
            fullNameInput.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            phoneInput.setError("Phone is required");
            phoneInput.requestFocus();
            return;
        }

        // Update display name
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(fullName)
                .build();

        currentUser.updateProfile(profileUpdates)
                .addOnSuccessListener(aVoid -> {
                    // TODO: Save phone and other data to Firestore
                    Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error updating profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
