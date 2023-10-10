package com.example.archvizarena.model.entity;

import jakarta.persistence.*;

import java.util.Date;
@Entity
@Table(name = "comments")
public class Comment extends BaseEntity{

    @Column(nullable = false)
    private boolean approved;
    @Column(nullable = false)
    private Date created;

    @Column(name = "text_content",
            columnDefinition = "TEXT",
            nullable = false)
    private String textContent;



    public Comment() {
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }


}
