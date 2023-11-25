package com.example.archvizarena.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
public class ApplicationEntity extends BaseEntity {

    @Column(name = "text_content",
            columnDefinition = "TEXT",
            nullable = false)
    private String textContent;

    @ManyToOne
    private UserEntity applicant;

    @ManyToOne
    private JobPublicationEntity jobPublication;

    private LocalDateTime submittedOn;

    public ApplicationEntity() {

    }

    public LocalDateTime getSubmittedOn() {
        return submittedOn;
    }

    public void setSubmittedOn(LocalDateTime submittedOn) {
        this.submittedOn = submittedOn;
    }

    public UserEntity getApplicant() {
        return applicant;
    }

    public void setApplicant(UserEntity applicant) {
        this.applicant = applicant;
    }

    public JobPublicationEntity getJobPublication() {
        return jobPublication;
    }

    public void setJobPublication(JobPublicationEntity jobPublication) {
        this.jobPublication = jobPublication;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
}
