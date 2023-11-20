package com.example.archvizarena.model.binding;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

public class CommentAddBindingModel {
    @Size(max = 1000)
    private String textContent;

    public CommentAddBindingModel() {
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
}
