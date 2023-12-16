package com.example.archvizarena.model.service;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class ReportSubmitServiceModel {
    private Long reportedProjectId;

    private Long reportedUserId;

    private String message;

    private Long reportingUserId;

    public ReportSubmitServiceModel() {
    }

    public Long getReportingUserId() {
        return reportingUserId;
    }

    public void setReportingUserId(Long reportingUserId) {
        this.reportingUserId = reportingUserId;
    }

    public Long getReportedProjectId() {
        return reportedProjectId;
    }

    public void setReportedProjectId(Long reportedProjectId) {
        this.reportedProjectId = reportedProjectId;
    }

    public Long getReportedUserId() {
        return reportedUserId;
    }

    public void setReportedUserId(Long reportedUserId) {
        this.reportedUserId = reportedUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
