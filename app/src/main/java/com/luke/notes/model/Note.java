package com.luke.notes.model;

import java.util.Date;

/**
 * Created by LSilva on 04/08/2014.
 */
public class Note {
    private String title;
    private String content;
    private Date lastModification;

    public Date getLastModification() {
        return lastModification;
    }

    public void setLastModification(Date lastModification) {
        this.lastModification = lastModification;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
