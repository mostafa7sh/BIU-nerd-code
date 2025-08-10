package com.example.gmailapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.gmailapp.repository.ComposeRepository;

public class ComposeViewModel extends ViewModel {

    private final ComposeRepository repository;

    public ComposeViewModel() {
        repository = new ComposeRepository();
    }

    public LiveData<Boolean> sendMail(String jwt, String to, String subject, String content) {
        return repository.sendMail(jwt, to, subject, content);
    }
}
