package com.financebuddha.finbud.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class JobApplicationRequestDTO {

    private String fullName;
    private String email;
    private String phone;
    private String selectedRole;
    private String experience;
    private String currentLocation;
    private String qualification;
    private String coverLetter;

    private MultipartFile resume;
}