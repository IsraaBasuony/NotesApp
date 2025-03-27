package com.example.noteapp;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class Note extends LitePalSupport {
    @Column(unique = true, defaultValue = "0")
    private int id;
    private int priority;
    private String title;
    private String description;

    public Note() {
    }

    public Note(String title, String description, int priority) {
        this.priority = priority;
        this.title = title;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}

