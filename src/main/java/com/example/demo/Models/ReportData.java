package com.example.demo.Models;

import java.util.List;

public class ReportData
{
    private String email;
    private List<ReportContent> content;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ReportContent> getContent() {
        return content;
    }

    public void setReportContent(List<ReportContent> content) {
        this.content = content;
    }
}
