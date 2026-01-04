package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.lostandfoundapp.utils.FirebaseUtils;

public class ProfileActivity extends AppCompatActivity {

    // Profile Views
    private ImageView profilePicture;
    private TextView userFullName;
    private TextView userEmail;
    private TextView userPhone;
    private Button editProfileButton;

    // Activity Views
    private TextView lostItemsCount;
    private TextView foundItemsCount;
    private Button viewLostItemsButton;
    private Button viewFoundItemsButton;

    // Notification Views
    private SwitchCompat matchNotificationToggle;
    private SwitchCompat messageAlertToggle;
    private SwitchCompat emailUpdateToggle;

    // Privacy Views
    private SwitchCompat shareContactToggle;
    private SwitchCompat allowMessagesToggle;
    private SwitchCompat publicProfileToggle;

    // Settings Views
    private Button changePasswordButton;
    private Button themeButton;
    private Button manageAccountButton;
    private Button logoutButton;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        // Initialize all views
        initializeViews();

        // Load user data
        loadUserProfile();

        // Setup listeners
        setupClickListeners();
        setupToggleListeners();
    }

    private void initializeViews() {
        // Profile
        profilePicture = findViewById(R.id.profilePicture);
        userFullName = findViewById(R.id.userFullName);
        userEmail = findViewById(R.id.userEmail);
        userPhone = findViewById(R.id.userPhone);
        editProfileButton = findViewById(R.id.editProfileButton);

        // Activity
        lostItemsCount = findViewById(R.id.lostItemsCount);
        foundItemsCount = findViewById(R.id.foundItemsCount);
        viewLostItemsButton = findViewById(R.id.viewLostItemsButton);
        viewFoundItemsButton = findViewById(R.id.viewFoundItemsButton);

        // Notifications
        matchNotificationToggle = findViewById(R.id.matchNotificationToggle);
        messageAlertToggle = findViewById(R.id.messageAlertToggle);
        emailUpdateToggle = findViewById(R.id.emailUpdateToggle);

        // Privacy
        shareContactToggle = findViewById(R.id.shareContactToggle);
        allowMessagesToggle = findViewById(R.id.allowMessagesToggle);
        publicProfileToggle = findViewById(R.id.publicProfileToggle);

        // Settings
        changePasswordButton = findViewById(R.id.changePasswordButton);
        themeButton = findViewById(R.id.themeButton);
        manageAccountButton = findViewById(R.id.manageAccountButton);
        logoutButton = findViewById(R.id.logoutButton);
    }

    private void loadUserProfile() {
        if (currentUser != null) {
            // Load email
            userEmail.setText(currentUser.getEmail());

            // Load name (if set)
            String displayName = currentUser.getDisplayName();
            if (displayName != null) {
                userFullName.setText(displayName);
            }

            // TODO: Load phone from Firestore user profile
            // TODO: Load profile picture from Firebase Storage

            // Load item counts
            loadItemCounts();
        }
    }

    private void loadItemCounts() {
        String userId = currentUser.getUid();

        // Get lost items count
        FirebaseUtils.getUserItems(userId, new FirebaseUtils.OnItemsRetrievedListener() {
            @Override
            public void onSuccess(com.google.firebase.firestore.QuerySnapshot querySnapshot) {
                if (querySnapshot != null) {
                    int lostCount = 0;
                    int foundCount = 0;

                    for (int i = 0; i < querySnapshot.size(); i++) {
                        Object isLostObj = querySnapshot.getDocuments().get(i).get("isLost");
                        if (isLostObj != null && (Boolean) isLostObj) {
                            lostCount++;
                        } else {
                            foundCount++;
                        }
                    }

                    lostItemsCount.setText(String.valueOf(lostCount));
                    foundItemsCount.setText(String.valueOf(foundCount));
                } else {
                    Toast.makeText(ProfileActivity.this, "No items found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(ProfileActivity.this, "Error loading items: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupClickListeners() {
        // Edit Profile
        if (editProfileButton != null) {
            editProfileButton.setOnClickListener(v -> editProfile());
        }

        // View Lost Items
        if (viewLostItemsButton != null) {
            viewLostItemsButton.setOnClickListener(v -> viewLostItems());
        }

        // View Found Items
        if (viewFoundItemsButton != null) {
            viewFoundItemsButton.setOnClickListener(v -> viewFoundItems());
        }

        // Change Password
        if (changePasswordButton != null) {
            changePasswordButton.setOnClickListener(v -> changePassword());
        }

        // Theme
        if (themeButton != null) {
            themeButton.setOnClickListener(v -> changeTheme());
        }

        // Manage Account
        if (manageAccountButton != null) {
            manageAccountButton.setOnClickListener(v -> manageAccount());
        }

        // Logout
        if (logoutButton != null) {
            logoutButton.setOnClickListener(v -> logout());
        }
    }

    private void setupToggleListeners() {
        // Notification toggles
        if (matchNotificationToggle != null) {
            matchNotificationToggle.setOnCheckedChangeListener((buttonView, isChecked) ->
                savePreference("match_notifications", isChecked)
            );
        }

        if (messageAlertToggle != null) {
            messageAlertToggle.setOnCheckedChangeListener((buttonView, isChecked) ->
                savePreference("message_alerts", isChecked)
            );
        }

        if (emailUpdateToggle != null) {
            emailUpdateToggle.setOnCheckedChangeListener((buttonView, isChecked) ->
                savePreference("email_updates", isChecked)
            );
        }

        // Privacy toggles
        if (shareContactToggle != null) {
            shareContactToggle.setOnCheckedChangeListener((buttonView, isChecked) ->
                savePreference("share_contact", isChecked)
            );
        }

        if (allowMessagesToggle != null) {
            allowMessagesToggle.setOnCheckedChangeListener((buttonView, isChecked) ->
                savePreference("allow_messages", isChecked)
            );
        }

        if (publicProfileToggle != null) {
            publicProfileToggle.setOnCheckedChangeListener((buttonView, isChecked) ->
                savePreference("public_profile", isChecked)
            );
        }
    }

    private void savePreference(String key, boolean value) {
        // TODO: Save to Firebase Firestore user preferences
        Toast.makeText(this, "Preference updated", Toast.LENGTH_SHORT).show();
    }

    private void editProfile() {
        // TODO: Navigate to EditProfileActivity
        // For now, show a dialog
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }

    private void viewLostItems() {
        // TODO: Navigate to lost items list
        Toast.makeText(this, "View Lost Items", Toast.LENGTH_SHORT).show();
    }

    private void viewFoundItems() {
        // TODO: Navigate to found items list
        Toast.makeText(this, "View Found Items", Toast.LENGTH_SHORT).show();
    }

    private void changePassword() {
        // TODO: Show password change dialog
        Toast.makeText(this, "Change Password - Coming Soon", Toast.LENGTH_SHORT).show();
    }

    private void changeTheme() {
        // TODO: Implement theme switching
        // For now toggle between light and dark
        boolean isDarkMode = getSharedPreferences("app_settings", MODE_PRIVATE)
                .getBoolean("dark_mode", false);
        
        getSharedPreferences("app_settings", MODE_PRIVATE)
                .edit()
                .putBoolean("dark_mode", !isDarkMode)
                .apply();
        
        // Recreate activity to apply theme
        recreate();
        
        Toast.makeText(this, isDarkMode ? "Light mode enabled" : "Dark mode enabled", Toast.LENGTH_SHORT).show();
    }

    private void manageAccount() {
        // TODO: Show account management options (edit, delete, etc.)
        Toast.makeText(this, "Manage Account - Coming Soon", Toast.LENGTH_SHORT).show();
    }

    private void logout() {
        firebaseAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
