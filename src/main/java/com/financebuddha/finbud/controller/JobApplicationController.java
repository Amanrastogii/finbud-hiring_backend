package com.financebuddha.finbud.controller;

import com.financebuddha.finbud.dto.*;
import com.financebuddha.finbud.entity.JobApplication;
import com.financebuddha.finbud.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
@CrossOrigin("*")
public class JobApplicationController {

    private final JobApplicationService service;

    // ✅ CREATE APPLICATION (PUBLIC)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<JobApplicationResponseDTO> createApplication(
            @ModelAttribute JobApplicationRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createApplication(request));
    }

    // 🔐 GET ALL APPLICATIONS
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

    // 🔐 GET SINGLE APPLICATION
    @GetMapping("/{id}")
    public ResponseEntity<?> getApplication(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Application not found");
        }
    }

    // 🔐 DOWNLOAD RESUME
    @GetMapping("/{id}/resume")
    public ResponseEntity<?> downloadResume(@PathVariable Long id) throws IOException {
        try {
            File file = service.getResumeFile(id);
            if (file == null || !file.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Resume file not found. It may have been deleted when the server restarted.");
            }
            Resource resource = new UrlResource(file.toURI());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=" + file.getName())
                    .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Resume not available");
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

    // 🔐 DELETE APPLICATION — NEW ENDPOINT
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

    // 🔐 DELETE ALL APPLICATIONS — for clearing test data
    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAllApplications() {
        try {
            service.deleteAllApplications();
            return ResponseEntity.ok(Map.of("message", "All applications deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting all applications");
        }
    }
}