package com.example.gmailapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.gmailapp.repository.AuthRepository;

public class LoginViewModel extends ViewModel {
    private final AuthRepository repository;

    public LoginViewModel() {
        repository = new AuthRepository();
    }

    public LiveData<String> login(String email, String password) {
        return repository.login(email, password);
    }
}
