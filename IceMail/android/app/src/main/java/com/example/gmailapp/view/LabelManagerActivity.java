package com.example.gmailapp.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gmailapp.R;
import com.example.gmailapp.model.Label;
import com.example.gmailapp.viewmodel.LabelViewModel;

import java.util.List;

public class LabelManagerActivity extends AppCompatActivity {

    private LabelViewModel labelViewModel;
    private LabelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_manager);

        labelViewModel = new ViewModelProvider(this).get(LabelViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewLabels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LabelAdapter(new java.util.ArrayList<>());
        recyclerView.setAdapter(adapter);

        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String jwt = prefs.getString("jwt", null);

        labelViewModel.getLabels(jwt).observe(this, labels -> adapter.updateList(labels));

        adapter.setOnLabelClickListener(label -> showEditDialog(jwt, label));
        adapter.setOnLabelLongClickListener(label -> showDeleteDialog(jwt, label));
    }

    private void showEditDialog(String jwt, Label label) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_label, null);
        EditText editLabel = dialogView.findViewById(R.id.editLabelName);
        editLabel.setText(label.getName());

        new AlertDialog.Builder(this)
                .setTitle("Edit Label")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newName = editLabel.getText().toString().trim();
                    if (!newName.isEmpty()) {
                        label.setName(newName);
                        labelViewModel.updateLabel(jwt, label).observe(this, success -> {
                            if (success) {
                                Toast.makeText(this, "Label updated", Toast.LENGTH_SHORT).show();
                                labelViewModel.getLabels(jwt).observe(this, labels -> adapter.updateList(labels));
                            } else {
                                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showDeleteDialog(String jwt, Label label) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Label")
                .setMessage("Are you sure you want to delete this label?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    labelViewModel.deleteLabel(jwt, label.getId()).observe(this, success -> {
                        if (success) {
                            Toast.makeText(this, "Label deleted", Toast.LENGTH_SHORT).show();
                            labelViewModel.getLabels(jwt).observe(this, labels -> adapter.updateList(labels));
                        } else {
                            Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
