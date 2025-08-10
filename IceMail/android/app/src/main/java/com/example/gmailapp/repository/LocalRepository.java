package com.example.gmailapp.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.gmailapp.local.AppDatabase;
import com.example.gmailapp.local.MailDao;
import com.example.gmailapp.local.MailEntity;

import java.util.List;

public class LocalRepository {
    private final MailDao mailDao;

    public LocalRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        mailDao = db.mailDao();
    }

    public LiveData<List<MailEntity>> getAllMails() {
        return mailDao.getAllMails();
    }

    public void insertMails(List<MailEntity> mails) {
        new Thread(() -> {
            mailDao.clearAll();
            mailDao.insertAll(mails);
        }).start();
    }
}