package com.example.archvizarena.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "job_publications")
public class JobPublication extends BaseProject{
    @Column(nullable = false)
    private BigDecimal budget;

    @ManyToOne
    private User buyer;

    @ManyToMany
    private List<User> applicants;

    @OneToMany
    private List<ApplicationMessage> receivedMessages;

    public JobPublication() {
    }

    public List<User> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<User> applicants) {
        this.applicants = applicants;
    }

    public List<ApplicationMessage> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<ApplicationMessage> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }
}
