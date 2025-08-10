package com.example.gmailapp.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MailEntity> mails);

    @Query("SELECT * FROM mails ORDER BY createdAt DESC")
    LiveData<List<MailEntity>> getAllMails();

    @Query("DELETE FROM mails")
    void clearAll();
}