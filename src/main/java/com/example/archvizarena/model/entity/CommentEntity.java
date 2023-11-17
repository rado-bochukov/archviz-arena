package com.example.archvizarena.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity{

    @Column(nullable = false)
    private boolean approved;
    @Column(nullable = false)
    private LocalDateTime created;

    @Column(name = "text_content",
            columnDefinition = "TEXT",
            nullable = false)
    private String textContent;

    @ManyToOne
    private UserEntity commentAuthor;



    public CommentEntity() {
    }

    public UserEntity getCommentAuthor() {
        return commentAuthor;
    }

    public void setCommentAuthor(UserEntity commentAuthor) {
        this.commentAuthor = commentAuthor;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
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


}
