package com.jobtracker.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.jobtracker.dto.JobApplicationsRequest;
import com.jobtracker.dto.JobApplicationsResponse;
import com.jobtracker.dto.UpdateStatusRequest;
import com.jobtracker.service.JobApplicationService;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    private final JobApplicationService service;

    public JobApplicationController(JobApplicationService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobApplicationsResponse create(@Valid @RequestBody JobApplicationsRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<JobApplicationsResponse> getAll(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String sort
    ) {
        return service.findAll(status, sort);
    }

    @GetMapping("/{id}")
    public JobApplicationsResponse getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PatchMapping("/{id}/status")
    public JobApplicationsResponse updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStatusRequest request
    ) {
        return service.updateStatus(id, request.getStatus());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
