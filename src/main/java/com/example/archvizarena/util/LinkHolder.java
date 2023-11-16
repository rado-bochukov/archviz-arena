package com.example.archvizarena.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LinkHolder {

    private List<String> imagesLink;


    public LinkHolder() {
        imagesLink=new ArrayList<>();
    }

    public List<String> getImagesLink() {
        return imagesLink;
    }

    public void setImagesLink(List<String> imagesLink) {
        this.imagesLink = imagesLink;
    }

    public  void clear(){
        this.setImagesLink(new ArrayList<>());
    }
}
