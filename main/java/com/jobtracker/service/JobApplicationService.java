package com.jobtracker.service;

import org.springframework.stereotype.Service;

import com.jobtracker.application.JobApplication;
import com.jobtracker.application.JobStatus;
import com.jobtracker.dto.JobApplicationsRequest;
import com.jobtracker.dto.JobApplicationsResponse;
import com.jobtracker.error.NotFoundException;
import com.jobtracker.repository.JobApplicationRepository;

import java.time.LocalDate;
import java.util.List;

/*
 * Service layer:
 * - holds business logic
 * - talks to repositories
 * - used by controllers
 */
@Service
public class JobApplicationService {

    // Repository handles all DB operations
    private final JobApplicationRepository repository;

    // Constructor injection by Spring
    public JobApplicationService(JobApplicationRepository repository) {
        this.repository = repository;
    }

    // Create a new job application
    public JobApplicationsResponse create(JobApplicationsRequest req) {

        JobApplication app = new JobApplication();

        // Copy validated input into entity
        app.setCompany(req.getCompany().trim());
        app.setPosition(req.getPosition().trim());

        // Default values for new applications
        app.setStatus(JobStatus.APPLIED);
        app.setAppliedDate(LocalDate.now());
        app.setJobUrl(req.getJobUrl());

        // Persist to database
        JobApplication saved = repository.save(app);

        return toResponse(saved);
    }

    // Fetch all applications, optionally filtered by status
    public List<JobApplicationsResponse> findAll(String status, String sort) {

        List<JobApplication> apps;

        if (status != null && !status.isBlank()) {
            JobStatus s = JobStatus.valueOf(status.trim().toUpperCase());
            apps = repository.findByStatus(s);
        } else {
            apps = repository.findAll();
        }

        // Convert entities to response DTOs
        return apps.stream()
                .map(this::toResponse)
                .toList();
    }

    // Fetch a single application by ID
    public JobApplicationsResponse findById(Long id) {

        JobApplication app = repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Job application with id " + id + " not found")
                );

        return toResponse(app);
    }

    // Update only the status field
    public JobApplicationsResponse updateStatus(Long id, JobStatus newStatus) {

        JobApplication app = repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Job application with id " + id + " not found")
                );

        app.setStatus(newStatus);

        JobApplication saved = repository.save(app);
        return toResponse(saved);
    }

    // Delete an application
    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new NotFoundException("Job application with id " + id + " not found");
        }

        repository.deleteById(id);
    }

    // Map entity to response DTO
    private JobApplicationsResponse toResponse(JobApplication a) {
        return new JobApplicationsResponse(
                a.getId(),
                a.getCompany(),
                a.getPosition(),
                a.getStatus(),
                a.getAppliedDate(),
                a.getJobUrl()
        );
    }
}
