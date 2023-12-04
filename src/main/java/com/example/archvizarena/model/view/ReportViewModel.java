package com.example.archvizarena.model.view;

public class ReportViewModel {

    private Long reportId;
    private String reportedUser;
    private String reportingUser;
    private String message;
    private Long reportedProjectId;

    private Long reportedUserId;
    private String reportedProjectTitle;



    public ReportViewModel() {
    }

    public Long getReportedUserId() {
        return reportedUserId;
    }

    public void setReportedUserId(Long reportedUserId) {
        this.reportedUserId = reportedUserId;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public String getReportedUser() {
        return reportedUser;
    }

    public void setReportedUser(String reportedUser) {
        this.reportedUser = reportedUser;
    }

    public String getReportingUser() {
        return reportingUser;
    }

    public void setReportingUser(String reportingUser) {
        this.reportingUser = reportingUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getReportedProjectId() {
        return reportedProjectId;
    }

    public void setReportedProjectId(Long reportedProjectId) {
        this.reportedProjectId = reportedProjectId;
    }

    public String getReportedProjectTitle() {
        return reportedProjectTitle;
    }

    public void setReportedProjectTitle(String reportedProjectTitle) {
        this.reportedProjectTitle = reportedProjectTitle;
    }

}
