package com.example.gmailapp.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gmailapp.R;
import com.example.gmailapp.model.Label;
import com.example.gmailapp.network.AuthService;
import com.example.gmailapp.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateLabelActivity extends AppCompatActivity {

    private EditText editLabelName;
    private Button btnCreateLabel;
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_label);

        authService = RetrofitClient.getInstance().create(AuthService.class);

        editLabelName = findViewById(R.id.editLabelName);
        btnCreateLabel = findViewById(R.id.btnCreateLabel);

        btnCreateLabel.setOnClickListener(v -> createLabel());
    }

    private void createLabel() {
        String labelName = editLabelName.getText().toString().trim();

        if (labelName.isEmpty()) {
            Toast.makeText(this, "Label name is required", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String jwt = prefs.getString("jwt", null);

        if (jwt == null) {
            Toast.makeText(this, "JWT not found", Toast.LENGTH_SHORT).show();
            return;
        }

        Label label = new Label();
        label.setName(labelName);

        authService.createLabel("Bearer " + jwt, label).enqueue(new Callback<Label>() {
            @Override
            public void onResponse(Call<Label> call, Response<Label> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CreateLabelActivity.this, "Label created!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(CreateLabelActivity.this, "Failed to create label", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Label> call, Throwable t) {
                Toast.makeText(CreateLabelActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
