package com.example.archvizarena.model.entity;

import com.example.archvizarena.model.entity.enums.PenaltyTypeEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "penalties")
public class PenaltyEntity extends BaseEntity{
    @OneToOne
    private UserEntity penalizedUser;

    @Column(name = "penalty-type")
    @Enumerated(EnumType.STRING)
    private PenaltyTypeEnum penalty;
    @Column
    private String message;

    @Column
    private LocalDateTime endDate;

    public PenaltyEntity() {
    }

    public UserEntity getPenalizedUser() {
        return penalizedUser;
    }

    public void setPenalizedUser(UserEntity penalizedUser) {
        this.penalizedUser = penalizedUser;
    }

    public PenaltyTypeEnum getPenalty() {
        return penalty;
    }

    public void setPenalty(PenaltyTypeEnum penalty) {
        this.penalty = penalty;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
