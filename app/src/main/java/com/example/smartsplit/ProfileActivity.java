package com.example.smartsplit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    EditText etName, etEmail, etBudget;
    Button btnSave;
    TextView tvProfileInfo;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);  // ✅ must match XML name

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etBudget = findViewById(R.id.etBudget);
        btnSave = findViewById(R.id.btnSave);
        tvProfileInfo = findViewById(R.id.tvProfileInfo);

        prefs = getSharedPreferences("UserProfile", MODE_PRIVATE);

        loadProfile();

        btnSave.setOnClickListener(v -> saveProfile());
    }

    private void saveProfile() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String budget = etBudget.getText().toString().trim();

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("budget", budget);
        editor.apply();

        tvProfileInfo.setText("Name: " + name + "\nEmail: " + email + "\nBudget: ₹" + budget);
    }

    private void loadProfile() {
        String name = prefs.getString("name", "");
        String email = prefs.getString("email", "");
        String budget = prefs.getString("budget", "");

        if (!name.isEmpty()) {
            etName.setText(name);
            etEmail.setText(email);
            etBudget.setText(budget);
            tvProfileInfo.setText("Name: " + name + "\nEmail: " + email + "\nBudget: ₹" + budget);
        }
    }
}
