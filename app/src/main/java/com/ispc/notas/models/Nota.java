package com.ispc.notas.models;

public class Nota {
    private long id;
    private String title;
    private String description;

    public Nota() {
    }

    public Nota(long noteId, String title, String description) {
        this.title = title;
        this.description = description;
        this.id = noteId;
    }

    public Nota(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
