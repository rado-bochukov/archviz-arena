package com.example.archvizarena.model.view;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class CommentViewModel {

    private Long id;

    private String authorName;
    @DateTimeFormat(pattern = "yyyy-MM-dd ")
    private String created;

    private String textContent;

    public CommentViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    // TODO: 22.11.2023 г. Date format of the comments
}
