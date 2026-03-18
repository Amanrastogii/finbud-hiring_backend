package com.financebuddha.finbud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobApplicationResponseDTO {

    private Long id;     // ✅ FIXED (was UUID)
    private String status;
}