package com.example.gmailapp.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.gmailapp.R;
import com.example.gmailapp.viewmodel.LoginViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailInput, passwordInput;
    private MaterialButton loginButton, signupButton;
    private LoginViewModel viewModel;

    private static final String TAG = "LOGIN_FLOW";
    private static final String PREFS_NAME = "app_prefs";
    private static final String TOKEN_KEY = "jwt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.editTextEmail);
        passwordInput = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);
        signupButton = findViewById(R.id.buttonSignup);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText() != null ? emailInput.getText().toString().trim() : "";
            String password = passwordInput.getText() != null ? passwordInput.getText().toString() : "";

            if (email.isEmpty() || password.isEmpty()) {
                Snackbar.make(v, "Please fill in all fields", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Snackbar.make(v, "Please enter a valid email address", Snackbar.LENGTH_SHORT).show();
                return;
            }

            viewModel.login(email, password).observe(this, token -> {
                if (token != null && !token.isEmpty()) {
                    saveToken(token);
                    Snackbar.make(v, "Login successful!", Snackbar.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, InboxActivity.class));
                    finish();
                } else {
                    Snackbar.make(v, "Login failed. Check credentials.", Snackbar.LENGTH_LONG).show();
                }
            });
        });

        signupButton.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class))
        );
    }

    private void saveToken(String token) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        prefs.edit().putString(TOKEN_KEY, token).apply();
    }
}
