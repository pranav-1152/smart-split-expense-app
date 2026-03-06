package com.example.smartsplit;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditSplitActivity extends AppCompatActivity {


    EditText etTripName;
    Button btnUpdate;

    SplitDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_split);

        etTripName = findViewById(R.id.etTripName);
        btnUpdate = findViewById(R.id.btnUpdate);

        db = new SplitDBHelper(this);

        String data = getIntent().getStringExtra("split_data");

        etTripName.setText(data);

        btnUpdate.setOnClickListener(v -> {

            // Update logic will be added
            finish();
        });
    }


}
