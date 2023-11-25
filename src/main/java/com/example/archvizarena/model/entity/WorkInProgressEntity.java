package com.example.archvizarena.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "work_in_progress")
public class WorkInProgressEntity extends BaseEntity{
    @OneToOne
    private JobPublicationEntity job;
    @ManyToOne
    private UserEntity buyer;
    @ManyToOne
    private UserEntity artist;

    public WorkInProgressEntity() {
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
