package com.example.gmailapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.gmailapp.model.Mail;
import com.example.gmailapp.repository.InboxRepository;

import java.util.List;

public class InboxViewModel extends ViewModel {
    private final InboxRepository repository;

    public InboxViewModel() {
        repository = new InboxRepository();
    }

    public LiveData<List<Mail>> getMails(String jwt) {
        return repository.getMails(jwt);
    }

    public LiveData<List<Mail>> searchMails(String jwt, String query) { return repository.searchMails(jwt, query); }

    public LiveData<Boolean> markAsImportant(String jwt, String mailId, boolean important) { return repository.markAsImportant(jwt, mailId, important); }

    public LiveData<List<Mail>> getMailsByLabel(String jwt, String labelId) { return repository.getMailsByLabel(jwt, labelId); }

    public LiveData<List<Mail>> getImportantMails(String jwt) { return repository.getImportantMails(jwt); }




}
