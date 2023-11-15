package com.example.archvizarena.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "job_publications")
public class JobPublicationEntity extends BaseProject{
    @Column(nullable = false)
    private BigDecimal budget;

    @ManyToOne
    private UserEntity buyer;

    @ManyToMany
    private List<UserEntity> applicants;

    @OneToMany
    private List<ApplicationMessageEntity> receivedMessages;

    public JobPublicationEntity() {
    }

    public List<UserEntity> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<UserEntity> applicants) {
        this.applicants = applicants;
    }

    public List<ApplicationMessageEntity> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<ApplicationMessageEntity> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public UserEntity getBuyer() {
        return buyer;
    }

    public void setBuyer(UserEntity buyer) {
        this.buyer = buyer;
    }
}
