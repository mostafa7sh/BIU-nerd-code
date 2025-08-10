package com.example.gmailapp.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MailEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MailDao mailDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "gmail_db").build();
                }
            }
        }
        return INSTANCE;
    }
}