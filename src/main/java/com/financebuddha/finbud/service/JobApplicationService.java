package com.financebuddha.finbud.service;

import com.financebuddha.finbud.dto.*;
import com.financebuddha.finbud.entity.JobApplication;
import com.financebuddha.finbud.entity.Status;
import com.financebuddha.finbud.repository.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository repo;
    private final FileStorageService fileStorage;
    private final EmailService emailService;

    public JobApplicationResponseDTO createApplication(JobApplicationRequestDTO dto) {
        try {
            // ✅ Uploads to S3, returns S3 key like "resumes/uuid.pdf"
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
                    .status(Status.SUBMITTED)
                    .build();

            app = repo.save(app);
            return new JobApplicationResponseDTO(app.getId(), app.getStatus().name());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while creating application: " + e.getMessage());
        }
    }

    public List<JobApplication> getAllApplications() {
        return repo.findAll();
    }

    public JobApplication getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }

    public JobApplication updateStatus(Long id, String status) {
        JobApplication app = getById(id);
        app.setStatus(Status.valueOf(status));
        return repo.save(app);
    }

    // ✅ Returns S3 InputStream for streaming to frontend
    public InputStream getResumeStream(Long id) {
        JobApplication app = getById(id);
        if (app.getResumeKey() == null || app.getResumeKey().isEmpty()) {
            throw new RuntimeException("No resume found for this application");
        }
        return fileStorage.downloadResume(app.getResumeKey());
    }

    public String getResumeKey(Long id) {
        return getById(id).getResumeKey();
    }

    // ✅ Delete single — also deletes from S3
    public void deleteApplication(Long id) {
        JobApplication app = getById(id);
        fileStorage.deleteResume(app.getResumeKey());
        repo.deleteById(id);
    }

    // ✅ Delete all — also deletes all S3 files
    public void deleteAllApplications() {
        List<JobApplication> all = repo.findAll();
        for (JobApplication app : all) {
            fileStorage.deleteResume(app.getResumeKey());
        }
        repo.deleteAll();
    }
}