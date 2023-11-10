package com.example.archvizarena.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "application_messages")
public class ApplicationMessage extends BaseEntity {


    @Column(name = "text_content",
            columnDefinition = "TEXT",
            nullable = false)
    private String textContent;





}
