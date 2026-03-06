package com.example.smartsplit;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewSplitsActivity extends AppCompatActivity {

    RecyclerView recyclerSplits;
    SplitDBHelper db;
    ArrayList<String> splits;
    SplitAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_splits);

        recyclerSplits = findViewById(R.id.recyclerSplits);
        db = new SplitDBHelper(this);

        recyclerSplits.setLayoutManager(new LinearLayoutManager(this));

        loadSplits();
    }

    private void loadSplits() {

        splits = db.getAllSplits();

        ArrayList<String> displayList = new ArrayList<>();

        for (String s : splits) {

            if (s.contains("|")) {
                displayList.add(s.substring(s.indexOf("|") + 1));
            }

        }

        adapter = new SplitAdapter(this, splits);
        recyclerSplits.setAdapter(adapter);
    }

}
