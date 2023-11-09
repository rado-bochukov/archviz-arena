package com.example.archvizarena.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "buyers")
public class Buyer extends BaseUser {
    @OneToMany(mappedBy = "buyer",
    fetch = FetchType.EAGER)
    private List<JobPublication> jobPublications;

    @OneToMany
    private List<Comment> commentsSent;



    public Buyer() {
    }

    public List<Comment> getSent() {
        return commentsSent;
    }

    public void setSent(List<Comment> sent) {
        this.commentsSent = sent;
    }

    public List<JobPublication> getJobPublications() {
        return jobPublications;
    }

    public void setJobPublications(List<JobPublication> jobPublications) {
        this.jobPublications = jobPublications;
    }
}
