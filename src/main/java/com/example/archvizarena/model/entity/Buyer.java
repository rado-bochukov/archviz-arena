package com.example.archvizarena.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "buyers")
public class Buyer extends BaseUser {
    @OneToMany(mappedBy = "buyer",
    fetch = FetchType.EAGER)
    private List<JobPublication> jobPublications;

    public Buyer() {
    }

    public List<JobPublication> getJobPublications() {
        return jobPublications;
    }

    public void setJobPublications(List<JobPublication> jobPublications) {
        this.jobPublications = jobPublications;
    }
}
