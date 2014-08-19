package com.luke.notes.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by LSilva on 04/08/2014.
 */
public class Note implements Serializable{
    private Integer id;
    private String title;
    private String content;
    private String lastModification;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastModification() {
        return lastModification;
    }

    public void setLastModification(String lastModification) {
        if(lastModification == null){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
            this.lastModification = formatter.format(new Date());
        } else {
            this.lastModification = lastModification;
        }
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
