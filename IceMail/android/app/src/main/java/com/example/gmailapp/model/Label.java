package com.example.gmailapp.model;

public class Label {
    private String _id;
    private String name;
    private String userId;


    public String getId() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public void setName(String labelName) {
        this.name = labelName;
    }
}