package com.financebuddha.finbud.service;

import com.financebuddha.finbud.dto.JobApplicationRequestDTO;
import com.financebuddha.finbud.dto.JobApplicationResponseDTO;
import com.financebuddha.finbud.entity.JobApplication;

import java.util.List;

public interface JobApplicationService {

    JobApplicationResponseDTO createApplication(JobApplicationRequestDTO requestDTO);

    JobApplicationResponseDTO getApplicationById(Long id);

    List<JobApplicationResponseDTO> getAllApplications();

    List<JobApplicationResponseDTO> getApplicationsByPosition(String position);

    List<JobApplicationResponseDTO> getApplicationsByStatus(JobApplication.ApplicationStatus status);

    JobApplicationResponseDTO updateApplicationStatus(Long id, JobApplication.ApplicationStatus status, String remarks);

    List<JobApplicationResponseDTO> searchApplications(String keyword);

    List<JobApplicationResponseDTO> getApplicationsByExperienceRange(Integer minExp, Integer maxExp);

    void deleteApplication(Long id);

    long getTotalApplications();

    long getCountByStatus(JobApplication.ApplicationStatus status);
}