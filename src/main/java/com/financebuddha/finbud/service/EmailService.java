package com.financebuddha.finbud.service;

import com.financebuddha.finbud.entity.JobApplication;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendEmails(JobApplication app) {
        System.out.println("Email skipped (not configured)");
    }
}