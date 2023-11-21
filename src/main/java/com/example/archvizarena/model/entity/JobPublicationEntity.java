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



    @OneToMany(mappedBy = "jobPublication" ,fetch = FetchType.EAGER)
    private List<ApplicationEntity> receivedMessages;

    public JobPublicationEntity() {
    }

    public List<ApplicationEntity> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<ApplicationEntity> receivedMessages) {
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
