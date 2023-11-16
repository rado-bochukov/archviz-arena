package com.example.archvizarena.model.view;

public class PictureUploadViewModel {

    private String name;

    public PictureUploadViewModel(String name) {
        this.name = name;
    }

    public PictureUploadViewModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
