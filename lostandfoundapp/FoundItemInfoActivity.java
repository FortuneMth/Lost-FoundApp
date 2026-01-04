package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FoundItemInfoActivity extends AppCompatActivity {
    
    private EditText itemNameEditText;
    private EditText colourEditText;
    private EditText descriptionEditText;
    private EditText foundDateEditText;
    private EditText foundTimeEditText;
    private Spinner categorySpinner;
    private Button backButton;
    private Button continueButton;

    private Calendar selectedCalendar;
    private String selectedCategory = "Other";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_item_info);

        selectedCalendar = Calendar.getInstance();

        itemNameEditText = findViewById(R.id.itemNameEditText);
        colourEditText = findViewById(R.id.colourEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        foundDateEditText = findViewById(R.id.foundDateEditText);
        foundTimeEditText = findViewById(R.id.foundTimeEditText);
        categorySpinner = findViewById(R.id.categorySpinner);
        backButton = findViewById(R.id.backButton);
        continueButton = findViewById(R.id.continueButton);

        // Setup category spinner
        setupCategorySpinner();

        // Date picker listener
        foundDateEditText.setOnClickListener(v -> showDatePicker());

        // Time picker listener
        foundTimeEditText.setOnClickListener(v -> showTimePicker());

        backButton.setOnClickListener(v -> onBackPressed());

        continueButton.setOnClickListener(v -> {
            if (validateInput()) {
                navigateToNextStep();
            }
        });
    }

    private void setupCategorySpinner() {
        String[] categories = {
            "Other",
            "Electronics",
            "Jewelry",
            "Clothing",
            "Accessories",
            "Bags",
            "Documents",
            "Sports Equipment",
            "Books",
            "Pets",
            "Keys",
            "Wallets",
            "Phones",
            "Watches",
            "Sunglasses"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_spinner_item,
            categories
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // Set default selection to "Other"
        categorySpinner.setSelection(0);

        // Listener to capture selected category
        categorySpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                selectedCategory = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                selectedCategory = "Other";
            }
        });
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    selectedCalendar.set(Calendar.YEAR, year);
                    selectedCalendar.set(Calendar.MONTH, month);
                    selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateDateDisplay();
                },
                selectedCalendar.get(Calendar.YEAR),
                selectedCalendar.get(Calendar.MONTH),
                selectedCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    selectedCalendar.set(Calendar.MINUTE, minute);
                    updateTimeDisplay();
                },
                selectedCalendar.get(Calendar.HOUR_OF_DAY),
                selectedCalendar.get(Calendar.MINUTE),
                true);
        timePickerDialog.show();
    }

    private void updateDateDisplay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        foundDateEditText.setText(dateFormat.format(selectedCalendar.getTime()));
    }

    private void updateTimeDisplay() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.US);
        foundTimeEditText.setText(timeFormat.format(selectedCalendar.getTime()));
    }

    private boolean validateInput() {
        String itemName = itemNameEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String date = foundDateEditText.getText().toString().trim();
        String time = foundTimeEditText.getText().toString().trim();

        if (itemName.isEmpty()) {
            itemNameEditText.setError("Item name is required");
            itemNameEditText.requestFocus();
            return false;
        }

        if (description.isEmpty()) {
            descriptionEditText.setError("Description is required");
            descriptionEditText.requestFocus();
            return false;
        }

        if (date.isEmpty()) {
            foundDateEditText.setError("Date is required");
            foundDateEditText.requestFocus();
            return false;
        }

        if (time.isEmpty()) {
            foundTimeEditText.setError("Time is required");
            foundTimeEditText.requestFocus();
            return false;
        }

        return true;
    }

    private void navigateToNextStep() {
        Intent intent = new Intent(this, FoundItemReportActivity.class);
        intent.putExtra("itemName", itemNameEditText.getText().toString());
        intent.putExtra("colour", colourEditText.getText().toString());
        intent.putExtra("description", descriptionEditText.getText().toString());
        intent.putExtra("foundDate", foundDateEditText.getText().toString());
        intent.putExtra("foundTime", foundTimeEditText.getText().toString());
        intent.putExtra("category", selectedCategory);
        startActivity(intent);
    }
}
