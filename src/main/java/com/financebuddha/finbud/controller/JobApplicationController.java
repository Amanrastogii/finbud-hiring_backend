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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
@CrossOrigin("*")
public class JobApplicationController {

    private final JobApplicationService service;

    // ✅ CREATE APPLICATION
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<JobApplicationResponseDTO> createApplication(
            @ModelAttribute JobApplicationRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createApplication(request));
    }

    // ✅ GET ALL APPLICATIONS
    @GetMapping
    public ResponseEntity<?> getAllApplications() {
        try {
            return ResponseEntity.ok(service.getAllApplications());
        } catch (Exception e) {
            e.printStackTrace(); // 🔥 shows error in terminal
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching applications");
        }
    }

    // ✅ GET SINGLE APPLICATION
    @GetMapping("/{id}")
    public JobApplication getApplication(@PathVariable Long id) {
        return service.getById(id);
    }

    // ✅ DOWNLOAD RESUME
    @GetMapping("/{id}/resume")
    public ResponseEntity<Resource> downloadResume(@PathVariable Long id) throws IOException {

        File file = service.getResumeFile(id);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new UrlResource(file.toURI());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .body(resource);
    }

    // ✅ UPDATE STATUS
    @PatchMapping("/{id}/status")
    public JobApplication updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        return service.updateStatus(id, body.get("status"));
    }
}