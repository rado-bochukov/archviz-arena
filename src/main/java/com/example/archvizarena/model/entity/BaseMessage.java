package com.example.archvizarena.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseMessage extends BaseEntity{


    @Column(nullable = false)
    private LocalDateTime created;

    @Column(name = "text_content",
            columnDefinition = "TEXT",
            nullable = false)
    private String textContent;

    @ManyToOne
    private UserEntity commentAuthor;

    public BaseMessage() {
    }



    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public UserEntity getCommentAuthor() {
        return commentAuthor;
    }

    public void setCommentAuthor(UserEntity commentAuthor) {
        this.commentAuthor = commentAuthor;
    }
}
