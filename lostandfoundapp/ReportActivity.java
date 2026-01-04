package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // Get the type from intent if coming from HomeFragment
        String type = getIntent().getStringExtra("type");

        // If type is specified, go directly to that flow
        if (type != null) {
            if ("lost".equals(type)) {
                startLostItemFlow();
            } else if ("found".equals(type)) {
                startFoundItemFlow();
            }
            finish();
            return;
        }

        // Otherwise, show selection screen
        CardView lostSomethingCard = findViewById(R.id.lostSomethingCard);
        CardView foundSomethingCard = findViewById(R.id.foundSomethingCard);

        if (lostSomethingCard != null) {
            lostSomethingCard.setOnClickListener(v -> startLostItemFlow());
        }

        if (foundSomethingCard != null) {
            foundSomethingCard.setOnClickListener(v -> startFoundItemFlow());
        }
    }

    private void startLostItemFlow() {
        Intent intent = new Intent(this, LostItemInfoActivity.class);
        startActivity(intent);
    }

    private void startFoundItemFlow() {
        Intent intent = new Intent(this, FoundItemInfoActivity.class);
        startActivity(intent);
    }
}

