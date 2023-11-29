package com.example.archvizarena.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reports")
public class ReportEntity extends BaseEntity {
    @ManyToOne
    private UserEntity reportingUser;
    @ManyToOne
    private CommentEntity reportedComment;
    @ManyToOne
    private PortfolioProjectEntity reportedProject;
    @ManyToOne
    private JobPublicationEntity reportedJobPublication;
    @ManyToOne
    private UserEntity reportedUser;

    @Column
    private String message;

    private boolean isChecked;


    public ReportEntity() {
    }

    public UserEntity getReportingUser() {
        return reportingUser;
    }

    public void setReportingUser(UserEntity reportingUser) {
        this.reportingUser = reportingUser;
    }

    public CommentEntity getReportedComment() {
        return reportedComment;
    }

    public void setReportedComment(CommentEntity reportedComment) {
        this.reportedComment = reportedComment;
    }

    public PortfolioProjectEntity getReportedProject() {
        return reportedProject;
    }

    public void setReportedProject(PortfolioProjectEntity reportedProject) {
        this.reportedProject = reportedProject;
    }

    public JobPublicationEntity getReportedJobPublication() {
        return reportedJobPublication;
    }

    public void setReportedJobPublication(JobPublicationEntity reportedJobPublications) {
        this.reportedJobPublication = reportedJobPublications;
    }

    public UserEntity getReportedUser() {
        return reportedUser;
    }

    public void setReportedUser(UserEntity reportedUser) {
        this.reportedUser = reportedUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
