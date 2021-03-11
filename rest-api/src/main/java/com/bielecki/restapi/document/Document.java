package com.bielecki.restapi.document;

import java.util.List;

public class Document {

    private long number;
    private String title;
    private List<String> tags;


    public Document(String title) {
        this.title = title;
    }

    public Document() {
    }


    public long getNumber() {
        return number;
    }
    public void setNumber(long number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
