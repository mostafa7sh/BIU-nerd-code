package com.example.gmailapp.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.gmailapp.R;
import com.example.gmailapp.viewmodel.RegisterViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText editUsername, editEmail, editPassword, editDob;
    private MaterialButton registerButton, chooseImageButton;
    private RegisterViewModel viewModel;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private android.widget.ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editUsername = findViewById(R.id.editTextUsername);
        editEmail = findViewById(R.id.editTextEmail);
        editPassword = findViewById(R.id.editTextPassword);
        editDob = findViewById(R.id.editTextDob);
        imageView = findViewById(R.id.imageViewProfile);
        registerButton = findViewById(R.id.buttonRegister);
        chooseImageButton = findViewById(R.id.buttonChooseImage);
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        editDob.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            new DatePickerDialog(this, (DatePicker view, int y, int m, int d) -> {
                String formatted = String.format("%04d-%02d-%02d", y, m + 1, d);
                editDob.setText(formatted);
            }, year, month, day).show();
        });
        chooseImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });
        registerButton.setOnClickListener(v -> {
            String username = getValue(editUsername);
            String email = getValue(editEmail);
            String password = getValue(editPassword);
            String dob = getValue(editDob);

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || dob.isEmpty()) {
                Snackbar.make(v, "Please fill in all fields", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 8) {
                Snackbar.make(v, "Password must be at least 8 characters", Snackbar.LENGTH_SHORT).show();
                return;
            }

            viewModel.register(username, email, password, dob, selectedImageUri, this)
                    .observe(this, token -> {
                        if (token != null) {
                            SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
                            prefs.edit().putString("jwt", token).apply();
                            startActivity(new Intent(this, InboxActivity.class));
                            finish();
                        } else {
                            Snackbar.make(v, "Registration failed", Snackbar.LENGTH_LONG).show();
                        }
                    });
        });
    }
    private String getValue(TextInputEditText input) {
        return input.getText() != null ? input.getText().toString().trim() : "";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            imageView.setImageURI(selectedImageUri);
        }
    }
}
