package com.example.archvizarena.model.view;

import java.time.LocalDateTime;

public class ApplicationViewModel {

    private Long id;

    private ArtistViewModel applicant;

    private String textContent;

    private LocalDateTime submittedOn;

    public ApplicationViewModel() {
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public LocalDateTime getSubmittedOn() {
        return submittedOn;
    }

    public void setSubmittedOn(LocalDateTime submittedOn) {
        this.submittedOn = submittedOn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArtistViewModel getApplicant() {
        return applicant;
    }

    public void setApplicant(ArtistViewModel applicant) {
        this.applicant = applicant;
    }




}
