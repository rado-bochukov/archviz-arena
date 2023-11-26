package com.example.archvizarena.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "work_in_progress")
public class WorkInProgressEntity extends BaseEntity{
    @OneToOne
    private JobPublicationEntity job;
    @ManyToOne
    private UserEntity buyer;
    @ManyToOne
    private UserEntity artist;

    @OneToMany(mappedBy = "workInProgress",
    fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<MessageEntity> messages;

    public WorkInProgressEntity() {
    }

    public List<MessageEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageEntity> messages) {
        this.messages = messages;
    }

    public JobPublicationEntity getJob() {
        return job;
    }

    public void setJob(JobPublicationEntity job) {
        this.job = job;
    }

    public UserEntity getBuyer() {
        return buyer;
    }

    public void setBuyer(UserEntity buyer) {
        this.buyer = buyer;
    }

    public UserEntity getArtist() {
        return artist;
    }

    public void setArtist(UserEntity artist) {
        this.artist = artist;
    }
}
