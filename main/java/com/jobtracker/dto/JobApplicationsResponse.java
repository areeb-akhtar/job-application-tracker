package com.jobtracker.dto;

import java.time.LocalDate;

import com.jobtracker.application.JobStatus;

public class JobApplicationsResponse {
    private Long id;
    private String company;
    private String position;
    private JobStatus status;
    private LocalDate appliedDate;
    private String jobUrl;

    public JobApplicationsResponse() {}

    public JobApplicationsResponse(Long id, String company, String position, JobStatus status, LocalDate appliedDate, String jobUrl) {
        this.id = id;
        this.company = company;
        this.position = position;
        this.status = status;
        this.appliedDate = appliedDate;
        this.jobUrl = jobUrl;
    }

    public Long getId() { return id; }
    public String getCompany() { return company; }
    public String getPosition() { return position; }
    public JobStatus getStatus() { return status; }
    public LocalDate getAppliedDate() { return appliedDate; }
    public String getJobUrl() { return jobUrl; }
}
