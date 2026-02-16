package com.financebuddha.finbud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 15)
    private String phone;

    @Column(nullable = false, length = 100)
    private String position;

    @Column(nullable = false)
    private Integer experience;

    @Column(nullable = false, length = 100)
    private String currentCompany;

    @Column(nullable = false, length = 100)
    private String currentLocation;

    @Column(nullable = false, length = 50)
    private String noticePeriod;

    @Column(nullable = false)
    private Double currentCTC;

    @Column(nullable = false)
    private Double expectedCTC;

    @Column(length = 200)
    private String resumeUrl;

    @Column(length = 200)
    private String linkedinUrl;

    @Column(length = 200)
    private String portfolioUrl;

    @Column(columnDefinition = "TEXT")
    private String skills;

    @Column(columnDefinition = "TEXT")
    private String coverLetter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ApplicationStatus status = ApplicationStatus.PENDING;

    @Column(length = 500)
    private String remarks;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public enum ApplicationStatus {
        PENDING,
        UNDER_REVIEW,
        SHORTLISTED,
        INTERVIEW_SCHEDULED,
        SELECTED,
        REJECTED,
        ON_HOLD
    }
}