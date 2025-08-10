package com.example.gmailapp.model;

import java.io.Serializable;
import com.google.gson.annotations.SerializedName;


public class Mail implements Serializable {
    private String from;
    private String to;
    private String subject;
    private String content;
    private String createdAt;
    @SerializedName("_id")
    private String id;

    private boolean important;
    private boolean draft;

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public boolean isImportant() { return important; }

    public void setImportant(boolean important) { this.important = important; }

    public String getId() { return id; }

    public boolean isDraft() { return draft; }

    public void setDraft(boolean important) { this.draft = draft; }

}
