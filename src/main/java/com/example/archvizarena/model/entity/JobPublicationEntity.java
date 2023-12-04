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


    @OneToMany(mappedBy = "jobPublication",
            fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<ApplicationEntity> applications;

    public JobPublicationEntity() {
    }
    public List<ApplicationEntity> getApplications() {
        return applications;
    }

    public void setApplications(List<ApplicationEntity> receivedMessages) {
        this.applications = receivedMessages;
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
