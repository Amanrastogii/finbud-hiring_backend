package com.financebuddha.finbud.repository;

import com.financebuddha.finbud.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    Optional<JobApplication> findByEmail(String email);

    boolean existsByEmail(String email);

    List<JobApplication> findByPosition(String position);

    List<JobApplication> findByStatus(JobApplication.ApplicationStatus status);

    List<JobApplication> findByPositionAndStatus(String position, JobApplication.ApplicationStatus status);

    @Query("SELECT j FROM JobApplication j WHERE " +
            "LOWER(j.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.position) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.skills) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<JobApplication> searchApplications(@Param("keyword") String keyword);

    @Query("SELECT j FROM JobApplication j WHERE j.experience BETWEEN :minExp AND :maxExp")
    List<JobApplication> findByExperienceRange(@Param("minExp") Integer minExp,
                                               @Param("maxExp") Integer maxExp);
}