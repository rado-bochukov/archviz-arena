package com.example.archvizarena.model.view;

import java.util.List;

public class WorkInProgressViewModel {

    private Long id;

    private JobPublicationViewModel job;
    private String buyerName;
    private String ArtistName;

    private List<MessageViewModel> messages;

    public WorkInProgressViewModel() {
    }

    public List<MessageViewModel> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageViewModel> messages) {
        this.messages = messages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobPublicationViewModel getJob() {
        return job;
    }

    public void setJob(JobPublicationViewModel job) {
        this.job = job;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getArtistName() {
        return ArtistName;
    }

    public void setArtistName(String artistName) {
        ArtistName = artistName;
    }
}
