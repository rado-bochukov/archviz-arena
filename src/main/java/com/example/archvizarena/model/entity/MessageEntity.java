package com.example.archvizarena.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "messages")
public class MessageEntity extends BaseMessage{

    @ManyToOne
    private WorkInProgressEntity workInProgress;

    public MessageEntity() {
    }

    public WorkInProgressEntity getWorkInProgress() {
        return workInProgress;
    }

    public void setWorkInProgress(WorkInProgressEntity workInProgress) {
        this.workInProgress = workInProgress;
    }
}
