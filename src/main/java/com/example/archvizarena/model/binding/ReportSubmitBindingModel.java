package com.example.archvizarena.model.binding;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ReportSubmitBindingModel {


    private Long reportedProjectId;

    private Long reportedUserId;
    @Size(min = 5)
    @NotEmpty
    private String message;

    public ReportSubmitBindingModel() {
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
