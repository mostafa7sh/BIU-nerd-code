package com.example.gmailapp.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gmailapp.R;
import com.example.gmailapp.view.LabelAdapter;
import com.example.gmailapp.model.Label;
import com.example.gmailapp.viewmodel.LabelViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class LabelActivity extends AppCompatActivity {

    private LabelViewModel viewModel;
    private LabelAdapter adapter;
    private RecyclerView recyclerView;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);

        viewModel = new ViewModelProvider(this).get(LabelViewModel.class);

        toolbar = findViewById(R.id.toolbarLabels);
        toolbar.setTitle("Labels");
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recyclerViewLabels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LabelAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        FloatingActionButton fabAddLabel = findViewById(R.id.fabAddLabel);
        fabAddLabel.setOnClickListener(v -> {
            loadLabels();
            Intent intent = new Intent(LabelActivity.this, CreateLabelActivity.class);
            startActivity(intent);
        });


        loadLabels();
    }

    private void loadLabels() {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String jwt = prefs.getString("jwt", null);

        if (jwt == null) {
            Toast.makeText(this, "Please login", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        viewModel.getLabels(jwt).observe(this, labels -> {
            if (labels != null) {
                adapter.updateList(labels);
            } else {
                Toast.makeText(this, "Failed to load labels", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
