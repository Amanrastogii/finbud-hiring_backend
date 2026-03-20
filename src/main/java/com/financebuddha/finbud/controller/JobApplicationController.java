package com.financebuddha.finbud.controller;

import com.financebuddha.finbud.dto.*;
import com.financebuddha.finbud.entity.JobApplication;
import com.financebuddha.finbud.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
@CrossOrigin("*")
public class JobApplicationController {

    private final JobApplicationService service;

    // ✅ CREATE — public, no auth needed
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<JobApplicationResponseDTO> createApplication(
            @ModelAttribute JobApplicationRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createApplication(request));
    }

    // 🔐 GET ALL
    @GetMapping
    public ResponseEntity<?> getAllApplications() {
        try {
            return ResponseEntity.ok(service.getAllApplications());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching applications");
        }
    }

    // 🔐 GET ONE
    @GetMapping("/{id}")
    public ResponseEntity<?> getApplication(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Application not found");
        }
    }

    // 🔐 DOWNLOAD RESUME — streams directly from S3
    @GetMapping("/{id}/resume")
    public ResponseEntity<?> downloadResume(@PathVariable Long id) {
        try {
            String resumeKey = service.getResumeKey(id);
            String filename  = resumeKey != null
                    ? resumeKey.substring(resumeKey.lastIndexOf("/") + 1)
                    : "resume.pdf";

            InputStream stream = service.getResumeStream(id);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(stream));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Resume not available: " + e.getMessage());
        }
    }

    // 🔐 UPDATE STATUS
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        try {
            return ResponseEntity.ok(service.updateStatus(id, body.get("status")));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating status");
        }
    }

    // 🔐 DELETE ONE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteApplication(@PathVariable Long id) {
        try {
            service.deleteApplication(id);
            return ResponseEntity.ok(Map.of("message", "Application deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting application");
        }
    }

    // 🔐 DELETE ALL — for clearing test data
    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAllApplications() {
        try {
            service.deleteAllApplications();
            return ResponseEntity.ok(Map.of("message", "All applications deleted"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting all applications");
        }
    }
}