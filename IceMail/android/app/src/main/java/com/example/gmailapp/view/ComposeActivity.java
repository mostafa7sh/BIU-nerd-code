package com.example.gmailapp.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.gmailapp.R;
import com.example.gmailapp.viewmodel.ComposeViewModel;
import com.google.android.material.appbar.MaterialToolbar;

public class ComposeActivity extends AppCompatActivity {

    private ComposeViewModel viewModel;
    private EditText editTextTo, editTextSubject, editTextBody;
    private String jwt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        jwt = prefs.getString("jwt", null);
        if (jwt == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        MaterialToolbar toolbar = findViewById(R.id.toolbarCompose);
        toolbar.setNavigationOnClickListener(v -> finish());
        editTextTo = findViewById(R.id.editTextTo);
        editTextSubject = findViewById(R.id.editTextSubject);
        editTextBody = findViewById(R.id.editTextBody);
        viewModel = new ViewModelProvider(this).get(ComposeViewModel.class);
        Button buttonSend = findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(v -> {
            String to = editTextTo.getText().toString().trim();
            String subject = editTextSubject.getText().toString().trim();
            String content = editTextBody.getText().toString().trim();
            if (to.isEmpty() || subject.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            viewModel.sendMail(jwt, to, subject, content).observe(this, success -> {
                if (Boolean.TRUE.equals(success)) {
                    Toast.makeText(this, "Mail sent", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Failed to send mail", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
