package com.example.smartsplit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("About SmartSplit");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView tvAbout = findViewById(R.id.tvAbout);

        tvAbout.setText(
                "💡 SmartSplit App v1.0\n\n" +

                        "SmartSplit is a simple expense sharing application that helps users divide expenses among friends quickly and easily.\n\n" +

                        "📌 Features:\n" +
                        "• Create a new expense split\n" +
                        "• Automatic expense calculation\n" +
                        "• View saved split history\n" +
                        "• Delete saved splits\n" +
                        "• Simple and user-friendly interface\n\n" +

                        "📱 How to Use:\n" +
                        "1. Open the app and click 'New Split'.\n" +
                        "2. Enter the trip/event name.\n" +
                        "3. Enter the number of people.\n" +
                        "4. Add each person's name and amount spent.\n" +
                        "5. Tap 'Calculate Split' to calculate expenses.\n" +
                        "6. The split will be saved automatically.\n" +
                        "7. Go to 'View Splits' to see your split history.\n" +
                        "8. Long press on any split to delete it.\n\n" +

                        "👨‍💻 Developed By:\n" +
                        "Pranav Satish Newase\n\n" +

                        "© 2026 SmartSplit App"
        );
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
