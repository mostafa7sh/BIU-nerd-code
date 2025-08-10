package com.example.gmailapp.model;

public class MailRequest {
    private String to;
    private String subject;
    private String body;

    public MailRequest(String to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public String getTo() { return to; }
    public String getSubject() { return subject; }
    public String getContent() { return body; }
}
