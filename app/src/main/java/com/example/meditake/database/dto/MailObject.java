package com.example.meditake.database.dto;

import java.util.List;

public class MailObject {
    private String toEmail,subject;
    private List<RapportDto> rapports;

    public MailObject() {
    }

    public MailObject(String toEmail, String subject, List<RapportDto> rapports) {
        this.toEmail = toEmail;
        this.subject = subject;
        this.rapports = rapports;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<RapportDto> getRapports() {
        return rapports;
    }

    public void setRapports(List<RapportDto> rapports) {
        this.rapports = rapports;
    }
}
