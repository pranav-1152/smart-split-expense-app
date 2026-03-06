package com.example.smartsplit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewSplitActivity extends AppCompatActivity {


    EditText etTripName, etNumPersons;
    LinearLayout layoutPersons;
    Button btnAddPersons, btnCalculateSplit;
    TextView tvResult;
    SplitDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_split);

        etTripName = findViewById(R.id.etTripName);
        etNumPersons = findViewById(R.id.etNumPersons);
        layoutPersons = findViewById(R.id.layoutPersons);
        btnAddPersons = findViewById(R.id.btnAddPersons);
        btnCalculateSplit = findViewById(R.id.btnCalculateSplit);
        tvResult = findViewById(R.id.tvResult);

        db = new SplitDBHelper(this);

        // ADD PERSON INPUT ROWS
        btnAddPersons.setOnClickListener(v -> {

            layoutPersons.removeAllViews();

            String numStr = etNumPersons.getText().toString().trim();

            if (numStr.isEmpty()) {
                Toast.makeText(this, "Enter number of persons", Toast.LENGTH_SHORT).show();
                return;
            }

            int num = Integer.parseInt(numStr);

            LayoutInflater inflater = LayoutInflater.from(this);

            for (int i = 0; i < num; i++) {

                View personView = inflater.inflate(
                        R.layout.item_person_input,
                        layoutPersons,
                        false
                );

                layoutPersons.addView(personView);
            }
        });

        // CALCULATE SPLIT
        btnCalculateSplit.setOnClickListener(v -> {

            String tripName = etTripName.getText().toString().trim();

            int count = layoutPersons.getChildCount();

            if (tripName.isEmpty()) {
                Toast.makeText(this, "Enter trip name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (count == 0) {
                Toast.makeText(this, "Add persons first", Toast.LENGTH_SHORT).show();
                return;
            }

            double total = 0;
            double[] spent = new double[count];
            String[] names = new String[count];

            try {

                for (int i = 0; i < count; i++) {

                    View row = layoutPersons.getChildAt(i);

                    EditText etName = row.findViewById(R.id.etPersonName);
                    EditText etAmount = row.findViewById(R.id.etPersonAmount);

                    names[i] = etName.getText().toString().trim();
                    spent[i] = Double.parseDouble(etAmount.getText().toString().trim());

                    total += spent[i];
                }

            } catch (Exception e) {

                Toast.makeText(this, "Enter valid name and amount", Toast.LENGTH_SHORT).show();
                return;
            }

            double share = total / count;

            double[] balance = new double[count];

            for (int i = 0; i < count; i++) {
                balance[i] = spent[i] - share;
            }

            StringBuilder result = new StringBuilder();

            result.append("Total Expense: ₹").append(total)
                    .append("\nEach Share: ₹").append(String.format("%.2f", share))
                    .append("\n\nTransactions:\n");

            boolean settled = false;

            while (!settled) {

                int maxCreditor = 0;
                int maxDebtor = 0;

                for (int i = 1; i < count; i++) {

                    if (balance[i] > balance[maxCreditor])
                        maxCreditor = i;

                    if (balance[i] < balance[maxDebtor])
                        maxDebtor = i;
                }

                if (Math.abs(balance[maxCreditor]) < 0.01 &&
                        Math.abs(balance[maxDebtor]) < 0.01) {

                    settled = true;
                    break;
                }

                double amount = Math.min(balance[maxCreditor], -balance[maxDebtor]);

                balance[maxCreditor] -= amount;
                balance[maxDebtor] += amount;

                result.append(names[maxDebtor])
                        .append(" pays ₹")
                        .append(String.format("%.2f", amount))
                        .append(" to ")
                        .append(names[maxCreditor])
                        .append("\n");
            }

            tvResult.setText(result.toString());

            // CURRENT DATE
            String date = new SimpleDateFormat(
                    "dd MMM yyyy",
                    Locale.getDefault()
            ).format(new Date());

            boolean success = db.insertSplit(
                    tripName,
                    total,
                    count,
                    result.toString(),
                    date
            );

            if (success)
                Toast.makeText(this, "Split saved!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Error saving split!", Toast.LENGTH_SHORT).show();
        });
    }


}
