package com.example.todoapp.model;

public class Task {
    private String title;
    private String description;
    private String lang;
    private String image;

    public Task(String title, String description, String lang, String image) {
        this.title = title;
        this.description = description;
        this.lang = lang;
        this.image = image;
    }

    public Task() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
