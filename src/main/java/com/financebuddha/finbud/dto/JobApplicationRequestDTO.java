package com.financebuddha.finbud.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationRequestDTO {

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phone;

    @NotBlank(message = "Position is required")
    private String position;

    @NotNull(message = "Experience is required")
    @Min(value = 0, message = "Experience cannot be negative")
    @Max(value = 50, message = "Experience seems invalid")
    private Integer experience;

    @NotBlank(message = "Current company is required")
    private String currentCompany;

    @NotBlank(message = "Current location is required")
    private String currentLocation;

    @NotBlank(message = "Notice period is required")
    private String noticePeriod;

    @NotNull(message = "Current CTC is required")
    @DecimalMin(value = "0.0", message = "Current CTC cannot be negative")
    private Double currentCTC;

    @NotNull(message = "Expected CTC is required")
    @DecimalMin(value = "0.0", message = "Expected CTC cannot be negative")
    private Double expectedCTC;

    private String resumeUrl;
    private String linkedinUrl;
    private String portfolioUrl;

    @NotBlank(message = "Skills are required")
    private String skills;

    private String coverLetter;
}