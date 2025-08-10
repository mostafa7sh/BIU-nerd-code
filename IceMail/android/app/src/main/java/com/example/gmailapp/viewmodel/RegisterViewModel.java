package com.example.gmailapp.viewmodel;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.gmailapp.repository.RegisterRepository;

public class RegisterViewModel extends ViewModel {
    private final RegisterRepository repository;

    public RegisterViewModel() {
        repository = new RegisterRepository();
    }

    public LiveData<String> register(String name, String email, String password, String dateOfBirth, Uri imageUri, Context context) {
        return repository.register(name, email, password, dateOfBirth, imageUri, context);
    }
}
