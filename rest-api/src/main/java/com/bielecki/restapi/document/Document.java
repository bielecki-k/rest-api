package com.bielecki.restapi.document;

public class Document {

    private String title;

    public Document(String title) {
        this.title = title;
    }

    public Document() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
