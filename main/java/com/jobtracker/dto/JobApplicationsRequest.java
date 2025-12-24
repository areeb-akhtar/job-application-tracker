package com.jobtracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class JobApplicationsRequest {

    @NotBlank(message = "company is required")
    @Size(max = 100, message = "company must be at most 100 characters")
    private String company;

    @NotBlank(message = "position is required")
    @Size(max = 100, message = "position must be at most 100 characters")
    private String position;

    @Size(max = 500, message = "jobUrl must be at most 500 characters")
    private String jobUrl;

    public String getCompany() { return company; }
    public String getPosition() { return position; }
    public String getJobUrl() { return jobUrl; }

    public void setCompany(String company) { this.company = company; }
    public void setPosition(String position) { this.position = position; }
    public void setJobUrl(String jobUrl) { this.jobUrl = jobUrl; }
}
