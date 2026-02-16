package com.financebuddha.finbud.dto;

import com.financebuddha.finbud.entity.JobApplication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationResponseDTO {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String position;
    private Integer experience;
    private String currentCompany;
    private String currentLocation;
    private String noticePeriod;
    private Double currentCTC;
    private Double expectedCTC;
    private String resumeUrl;
    private String linkedinUrl;
    private String portfolioUrl;
    private String skills;
    private String coverLetter;
    private JobApplication.ApplicationStatus status;
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}