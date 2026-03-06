package com.example.smartsplit;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity {
    
    Button btnNewSplit, btnViewSplits, btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnNewSplit = findViewById(R.id.btnNewSplit);
        btnViewSplits = findViewById(R.id.btnViewSplits);
        btnProfile = findViewById(R.id.btnProfile);

        btnNewSplit.setOnClickListener(v ->
                startActivity(new Intent(this, NewSplitActivity.class)));

        btnViewSplits.setOnClickListener(v ->
                startActivity(new Intent(this, ViewSplitsActivity.class)));

        btnProfile.setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_darkmode) {

            int currentMode = getResources().getConfiguration().uiMode
                    & Configuration.UI_MODE_NIGHT_MASK;

            if (currentMode == Configuration.UI_MODE_NIGHT_YES) {

                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO
                );

                Toast.makeText(this, "Light Mode Enabled", Toast.LENGTH_SHORT).show();

            } else {

                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES
                );

                Toast.makeText(this, "Dark Mode Enabled", Toast.LENGTH_SHORT).show();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
