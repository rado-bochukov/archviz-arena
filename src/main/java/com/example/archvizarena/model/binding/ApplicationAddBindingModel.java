package com.example.archvizarena.model.binding;


import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Service;

public class ApplicationAddBindingModel {
    @Size(min = 5)
    private String textContent;

    public ApplicationAddBindingModel() {
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
}
