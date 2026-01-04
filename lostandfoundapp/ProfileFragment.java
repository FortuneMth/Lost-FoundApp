package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.lostandfoundapp.utils.FirebaseUtils;

public class ProfileFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    private TextView userFullName;
    private TextView userEmail;
    private TextView lostItemsCount;
    private TextView foundItemsCount;

    private Button editProfileButton;
    private Button viewItemsButton;
    private Button changePasswordButton;
    private Button themeButton;
    private Button manageAccountButton;
    private Button logoutButton;

    private SwitchCompat matchNotificationsSwitch;
    private SwitchCompat messageAlertsSwitch;
    private SwitchCompat emailUpdatesSwitch;
    private SwitchCompat contactInfoSwitch;
    private SwitchCompat allowMessagesSwitch;
    private SwitchCompat publicProfileSwitch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        // Initialize views
        initializeViews(view);

        // Load user data
        loadUserProfile();

        // Setup listeners
        setupClickListeners();
        setupToggleListeners();
    }

    private void initializeViews(View view) {
        userFullName = view.findViewById(R.id.userFullName);
        userEmail = view.findViewById(R.id.userEmail);
        lostItemsCount = view.findViewById(R.id.lostItemsCount);
        foundItemsCount = view.findViewById(R.id.foundItemsCount);

        editProfileButton = view.findViewById(R.id.editProfileButton);
        viewItemsButton = view.findViewById(R.id.viewItemsButton);
        changePasswordButton = view.findViewById(R.id.changePasswordButton);
        themeButton = view.findViewById(R.id.themeButton);
        manageAccountButton = view.findViewById(R.id.manageAccountButton);
        logoutButton = view.findViewById(R.id.logoutButton);

        matchNotificationsSwitch = view.findViewById(R.id.matchNotificationsSwitch);
        messageAlertsSwitch = view.findViewById(R.id.messageAlertsSwitch);
        emailUpdatesSwitch = view.findViewById(R.id.emailUpdatesSwitch);
        contactInfoSwitch = view.findViewById(R.id.contactInfoSwitch);
        allowMessagesSwitch = view.findViewById(R.id.allowMessagesSwitch);
        publicProfileSwitch = view.findViewById(R.id.publicProfileSwitch);
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

            // Load item counts
            loadItemCounts();
        }
    }

    private void loadItemCounts() {
        String userId = currentUser.getUid();

        // Get items count
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
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(getContext(), "Error loading items: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupClickListeners() {
        editProfileButton.setOnClickListener(v -> editProfile());
        viewItemsButton.setOnClickListener(v -> viewItems());
        changePasswordButton.setOnClickListener(v -> changePassword());
        themeButton.setOnClickListener(v -> changeTheme());
        manageAccountButton.setOnClickListener(v -> manageAccount());
        logoutButton.setOnClickListener(v -> logout());
    }

    private void setupToggleListeners() {
        matchNotificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
            savePreference("notifications_match", isChecked));
        messageAlertsSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
            savePreference("notifications_messages", isChecked));
        emailUpdatesSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
            savePreference("notifications_email", isChecked));
        contactInfoSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
            savePreference("privacy_contact", isChecked));
        allowMessagesSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
            savePreference("privacy_messages", isChecked));
        publicProfileSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
            savePreference("privacy_public", isChecked));
    }

    private void savePreference(String key, boolean value) {
        // TODO: Save preferences to Firestore
        Toast.makeText(getContext(), "Preference saved", Toast.LENGTH_SHORT).show();
    }

    private void editProfile() {
        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
        startActivity(intent);
    }

    private void viewItems() {
        // TODO: Show list of user's items
        Toast.makeText(getContext(), "View items feature coming soon", Toast.LENGTH_SHORT).show();
    }

    private void changePassword() {
        // TODO: Implement password change dialog
        Toast.makeText(getContext(), "Change password feature coming soon", Toast.LENGTH_SHORT).show();
    }

    private void changeTheme() {
        // TODO: Toggle dark/light mode
        Toast.makeText(getContext(), "Theme change feature coming soon", Toast.LENGTH_SHORT).show();
    }

    private void manageAccount() {
        // TODO: Show account management options
        Toast.makeText(getContext(), "Manage account feature coming soon", Toast.LENGTH_SHORT).show();
    }

    private void logout() {
        firebaseAuth.signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}