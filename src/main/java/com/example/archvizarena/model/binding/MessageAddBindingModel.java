package com.example.archvizarena.model.binding;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MessageAddBindingModel {
    @NotEmpty
    @NotNull
    private String textContent;

    public MessageAddBindingModel() {
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
}
