package com.financebuddha.finbud.service;

import com.financebuddha.finbud.dto.JobApplicationRequestDTO;
import com.financebuddha.finbud.dto.JobApplicationResponseDTO;
import com.financebuddha.finbud.entity.JobApplication;
import com.financebuddha.finbud.exception.DuplicateResourceException;
import com.financebuddha.finbud.exception.ResourceNotFoundException;
import com.financebuddha.finbud.repository.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository applicationRepository;
    private final ModelMapper modelMapper;

    @Override
    public JobApplicationResponseDTO createApplication(JobApplicationRequestDTO requestDTO) {
        log.info("Creating new job application for email: {}", requestDTO.getEmail());

        if (applicationRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("Application", "email", requestDTO.getEmail());
        }

        JobApplication application = modelMapper.map(requestDTO, JobApplication.class);
        application.setStatus(JobApplication.ApplicationStatus.PENDING);

        JobApplication savedApplication = applicationRepository.save(application);

        log.info("Application created successfully with ID: {}", savedApplication.getId());

        return modelMapper.map(savedApplication, JobApplicationResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public JobApplicationResponseDTO getApplicationById(Long id) {
        log.info("Fetching application with ID: {}", id);

        JobApplication application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application", "id", id));

        return modelMapper.map(application, JobApplicationResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobApplicationResponseDTO> getAllApplications() {
        log.info("Fetching all applications");

        List<JobApplication> applications = applicationRepository.findAll();

        return applications.stream()
                .map(app -> modelMapper.map(app, JobApplicationResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobApplicationResponseDTO> getApplicationsByPosition(String position) {
        log.info("Fetching applications for position: {}", position);

        List<JobApplication> applications = applicationRepository.findByPosition(position);

        return applications.stream()
                .map(app -> modelMapper.map(app, JobApplicationResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobApplicationResponseDTO> getApplicationsByStatus(JobApplication.ApplicationStatus status) {
        log.info("Fetching applications with status: {}", status);

        List<JobApplication> applications = applicationRepository.findByStatus(status);

        return applications.stream()
                .map(app -> modelMapper.map(app, JobApplicationResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public JobApplicationResponseDTO updateApplicationStatus(Long id,
                                                             JobApplication.ApplicationStatus status,
                                                             String remarks) {
        log.info("Updating status for application ID: {} to {}", id, status);

        JobApplication application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application", "id", id));

        application.setStatus(status);
        if (remarks != null && !remarks.isEmpty()) {
            application.setRemarks(remarks);
        }

        JobApplication updatedApplication = applicationRepository.save(application);

        log.info("Application status updated successfully");

        return modelMapper.map(updatedApplication, JobApplicationResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobApplicationResponseDTO> searchApplications(String keyword) {
        log.info("Searching applications with keyword: {}", keyword);

        List<JobApplication> applications = applicationRepository.searchApplications(keyword);

        return applications.stream()
                .map(app -> modelMapper.map(app, JobApplicationResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobApplicationResponseDTO> getApplicationsByExperienceRange(Integer minExp, Integer maxExp) {
        log.info("Fetching applications with experience range: {} - {}", minExp, maxExp);

        List<JobApplication> applications = applicationRepository.findByExperienceRange(minExp, maxExp);

        return applications.stream()
                .map(app -> modelMapper.map(app, JobApplicationResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteApplication(Long id) {
        log.info("Deleting application with ID: {}", id);

        if (!applicationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Application", "id", id);
        }

        applicationRepository.deleteById(id);
        log.info("Application deleted successfully");
    }

    @Override
    @Transactional(readOnly = true)
    public long getTotalApplications() {
        return applicationRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public long getCountByStatus(JobApplication.ApplicationStatus status) {
        return applicationRepository.findByStatus(status).size();
    }
}