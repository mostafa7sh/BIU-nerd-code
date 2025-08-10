package com.example.gmailapp.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mails")
public class MailEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String from;
    public String to;
    public String subject;
    public String content;
    public String createdAt;
}