package com.financebuddha.finbud.controller;

import com.financebuddha.finbud.dto.ApiResponse;
import com.financebuddha.finbud.dto.JobApplicationRequestDTO;
import com.financebuddha.finbud.dto.JobApplicationResponseDTO;
import com.financebuddha.finbud.entity.JobApplication;
import com.financebuddha.finbud.service.JobApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class JobApplicationController {

    private final JobApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApiResponse<JobApplicationResponseDTO>> createApplication(
            @Valid @RequestBody JobApplicationRequestDTO requestDTO) {

        log.info("Received application for position: {}", requestDTO.getPosition());

        JobApplicationResponseDTO responseDTO = applicationService.createApplication(requestDTO);

        ApiResponse<JobApplicationResponseDTO> response = ApiResponse.success(
                "Application submitted successfully! We will contact you soon.",
                responseDTO
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<JobApplicationResponseDTO>> getApplicationById(
            @PathVariable Long id) {

        log.info("Fetching application with ID: {}", id);

        JobApplicationResponseDTO responseDTO = applicationService.getApplicationById(id);

        ApiResponse<JobApplicationResponseDTO> response = ApiResponse.success(
                "Application retrieved successfully",
                responseDTO
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<JobApplicationResponseDTO>>> getAllApplications() {

        log.info("Fetching all applications");

        List<JobApplicationResponseDTO> applications = applicationService.getAllApplications();

        ApiResponse<List<JobApplicationResponseDTO>> response = ApiResponse.success(
                "Applications retrieved successfully",
                applications
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/position/{position}")
    public ResponseEntity<ApiResponse<List<JobApplicationResponseDTO>>> getApplicationsByPosition(
            @PathVariable String position) {

        log.info("Fetching applications for position: {}", position);

        List<JobApplicationResponseDTO> applications = applicationService.getApplicationsByPosition(position);

        ApiResponse<List<JobApplicationResponseDTO>> response = ApiResponse.success(
                "Applications retrieved successfully",
                applications
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<JobApplicationResponseDTO>>> getApplicationsByStatus(
            @PathVariable JobApplication.ApplicationStatus status) {

        log.info("Fetching applications with status: {}", status);

        List<JobApplicationResponseDTO> applications = applicationService.getApplicationsByStatus(status);

        ApiResponse<List<JobApplicationResponseDTO>> response = ApiResponse.success(
                "Applications retrieved successfully",
                applications
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<JobApplicationResponseDTO>> updateApplicationStatus(
            @PathVariable Long id,
            @RequestParam JobApplication.ApplicationStatus status,
            @RequestParam(required = false) String remarks) {

        log.info("Updating status for application ID: {} to {}", id, status);

        JobApplicationResponseDTO responseDTO = applicationService.updateApplicationStatus(id, status, remarks);

        ApiResponse<JobApplicationResponseDTO> response = ApiResponse.success(
                "Application status updated successfully",
                responseDTO
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<JobApplicationResponseDTO>>> searchApplications(
            @RequestParam String keyword) {

        log.info("Searching applications with keyword: {}", keyword);

        List<JobApplicationResponseDTO> applications = applicationService.searchApplications(keyword);

        ApiResponse<List<JobApplicationResponseDTO>> response = ApiResponse.success(
                "Search completed successfully",
                applications
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/experience")
    public ResponseEntity<ApiResponse<List<JobApplicationResponseDTO>>> getApplicationsByExperience(
            @RequestParam Integer min,
            @RequestParam Integer max) {

        log.info("Fetching applications with experience range: {} - {}", min, max);

        List<JobApplicationResponseDTO> applications =
                applicationService.getApplicationsByExperienceRange(min, max);

        ApiResponse<List<JobApplicationResponseDTO>> response = ApiResponse.success(
                "Applications retrieved successfully",
                applications
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteApplication(@PathVariable Long id) {

        log.info("Deleting application with ID: {}", id);

        applicationService.deleteApplication(id);

        ApiResponse<Object> response = ApiResponse.success(
                "Application deleted successfully",
                null
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStatistics() {

        log.info("Fetching application statistics");

        Map<String, Object> stats = new HashMap<>();
        stats.put("total", applicationService.getTotalApplications());
        stats.put("pending", applicationService.getCountByStatus(JobApplication.ApplicationStatus.PENDING));
        stats.put("underReview", applicationService.getCountByStatus(JobApplication.ApplicationStatus.UNDER_REVIEW));
        stats.put("shortlisted", applicationService.getCountByStatus(JobApplication.ApplicationStatus.SHORTLISTED));
        stats.put("selected", applicationService.getCountByStatus(JobApplication.ApplicationStatus.SELECTED));
        stats.put("rejected", applicationService.getCountByStatus(JobApplication.ApplicationStatus.REJECTED));

        ApiResponse<Map<String, Object>> response = ApiResponse.success(
                "Statistics retrieved successfully",
                stats
        );

        return ResponseEntity.ok(response);
    }
}
