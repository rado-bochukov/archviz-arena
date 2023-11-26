package com.example.archvizarena.model.service;

public class MessageAddServiceModel {
    private Long workInProgressId;
    private String textContent;

    private String authorUserName;

    public MessageAddServiceModel() {
    }

    public Long getWorkInProgressId() {
        return workInProgressId;
    }

    public void setWorkInProgressId(Long workInProgressId) {
        this.workInProgressId = workInProgressId;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getAuthorUserName() {
        return authorUserName;
    }

    public void setAuthorUserName(String authorUserName) {
        this.authorUserName = authorUserName;
    }
}
