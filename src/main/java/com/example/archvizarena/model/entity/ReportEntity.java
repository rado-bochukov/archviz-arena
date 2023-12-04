package com.example.archvizarena.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class ReportEntity extends BaseEntity {
    @ManyToOne
    private UserEntity reportingUser;

    @ManyToOne
    private PortfolioProjectEntity reportedProject;

    @ManyToOne
    private UserEntity reportedUser;

    @Column
    private String message;
    @Column
    private boolean isArchived;

    @Column
    private LocalDateTime archivedUntil;


    public ReportEntity() {
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public LocalDateTime getArchivedUntil() {
        return archivedUntil;
    }

    public void setArchivedUntil(LocalDateTime archivedUntil) {
        this.archivedUntil = archivedUntil;
    }

    public UserEntity getReportingUser() {
        return reportingUser;
    }

    public void setReportingUser(UserEntity reportingUser) {
        this.reportingUser = reportingUser;
    }

    public PortfolioProjectEntity getReportedProject() {
        return reportedProject;
    }

    public void setReportedProject(PortfolioProjectEntity reportedProject) {
        this.reportedProject = reportedProject;
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


}
