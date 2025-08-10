package com.example.gmailapp.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.gmailapp.R;
import com.example.gmailapp.model.Label;
import com.example.gmailapp.model.Mail;
import com.example.gmailapp.viewmodel.InboxViewModel;
import com.example.gmailapp.viewmodel.LabelViewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewMailActivity extends AppCompatActivity {
    private Mail mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mail);

        mail = (Mail) getIntent().getSerializableExtra("mail");

        TextView fromText = findViewById(R.id.textFrom);
        TextView toText = findViewById(R.id.textTo);
        TextView subjectText = findViewById(R.id.textSubject);
        TextView contentText = findViewById(R.id.textContent);
        TextView dateText = findViewById(R.id.textDate);

        fromText.setText("From: " + mail.getFrom());
        toText.setText("To: " + mail.getTo());
        subjectText.setText(mail.getSubject());
        contentText.setText(mail.getContent());
        dateText.setText(mail.getCreatedAt());

    }

    private void showAssignLabelsDialog() {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String jwt = prefs.getString("jwt", null);

        if (jwt == null) {
            Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        // Step 1: Load labels from server
        LabelViewModel viewModel = new ViewModelProvider(this).get(LabelViewModel.class);
        viewModel.getLabels(jwt).observe(this, labels -> {
            if (labels == null || labels.isEmpty()) {
                Toast.makeText(this, "No labels available", Toast.LENGTH_SHORT).show();
                return;
            }

            String[] labelNames = new String[labels.size()];
            boolean[] checked = new boolean[labels.size()];
            for (int i = 0; i < labels.size(); i++) {
                labelNames[i] = labels.get(i).getName();
                checked[i] = false;
            }

            new AlertDialog.Builder(this)
                    .setTitle("Assign Labels")
                    .setMultiChoiceItems(labelNames, checked, (dialog, indexSelected, isChecked) -> {
                        checked[indexSelected] = isChecked;
                    })
                    .setPositiveButton("Assign", (dialog, which) -> {
                        List<String> selectedLabelIds = new ArrayList<>();
                        for (int i = 0; i < checked.length; i++) {
                            if (checked[i]) {
                                selectedLabelIds.add(labels.get(i).getId());
                            }
                        }

                        if (!selectedLabelIds.isEmpty()) {
                            viewModel.assignLabelsToMail(jwt, mail.getId(), selectedLabelIds)
                                    .observe(this, success -> {
                                        if (success) {
                                            Toast.makeText(this, "Labels assigned", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(this, "Failed to assign labels", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_assign_labels) {
            showAssignLabelsDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
