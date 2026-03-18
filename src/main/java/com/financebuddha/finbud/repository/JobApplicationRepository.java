package com.financebuddha.finbud.repository;

import com.financebuddha.finbud.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
}