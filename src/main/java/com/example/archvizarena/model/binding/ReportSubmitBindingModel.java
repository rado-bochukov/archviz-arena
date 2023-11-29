package com.example.archvizarena.model.binding;


import jakarta.validation.constraints.Size;

public class ReportSubmitBindingModel {

    private Long commentId;

    private Long reportedProjectId;

    private Long reportedJobPublicationId;

    private Long reportedUserId;
    @Size(min = 5)
    private String message;

    public ReportSubmitBindingModel() {
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getReportedProjectId() {
        return reportedProjectId;
    }

    public void setReportedProjectId(Long reportedProjectId) {
        this.reportedProjectId = reportedProjectId;
    }

    public Long getReportedJobPublicationId() {
        return reportedJobPublicationId;
    }

    public void setReportedJobPublicationId(Long reportedJobPublicationId) {
        this.reportedJobPublicationId = reportedJobPublicationId;
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
