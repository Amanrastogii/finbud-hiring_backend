package com.financebuddha.finbud.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // ✅ FIXED (was UUID)

    private String fullName;
    private String email;
    private String phone;

    private String selectedRole;
    private String experience;
    private String currentLocation;
    private String qualification;

    @Column(length = 2000)
    private String coverLetter;

    private String resumeKey;

    private String status = "SUBMITTED";
}