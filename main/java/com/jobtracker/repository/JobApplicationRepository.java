package com.jobtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobtracker.application.JobApplication;
import com.jobtracker.application.JobStatus;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByStatus(JobStatus status);
}
