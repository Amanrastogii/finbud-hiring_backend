package com.financebuddha.finbud.service;

import com.financebuddha.finbud.dto.*;
import com.financebuddha.finbud.entity.JobApplication;
import com.financebuddha.finbud.entity.Status; // ✅ ADDED
import com.financebuddha.finbud.repository.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository repo;
    private final FileStorageService fileStorage;
    private final EmailService emailService;

    public JobApplicationResponseDTO createApplication(JobApplicationRequestDTO dto) {

        try {
            String resumeKey = fileStorage.uploadResume(dto.getResume());

            JobApplication app = JobApplication.builder()
                    .fullName(dto.getFullName())
                    .email(dto.getEmail())
                    .phone(dto.getPhone())
                    .selectedRole(dto.getSelectedRole())
                    .experience(dto.getExperience())
                    .currentLocation(dto.getCurrentLocation())
                    .qualification(dto.getQualification())
                    .coverLetter(dto.getCoverLetter())
                    .resumeKey(resumeKey)
                    .status(Status.SUBMITTED)   // ✅ FIXED
                    .build();

            app = repo.save(app);

            return new JobApplicationResponseDTO(
                    app.getId(),
                    app.getStatus().name()   // ✅ FIXED (Enum → String)
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while creating application");
        }
    }

    public List<JobApplication> getAllApplications() {
        try {
            return repo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch applications");
        }
    }

    public JobApplication getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }

    public JobApplication updateStatus(Long id, String status) {
        JobApplication app = getById(id);

        // ✅ FIXED (String → Enum)
        app.setStatus(Status.valueOf(status));

        return repo.save(app);
    }

    public File getResumeFile(Long id) {
        JobApplication app = getById(id);
        return new File("uploads/" + app.getResumeKey());
    }
}