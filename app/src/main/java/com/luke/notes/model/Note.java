package com.luke.notes.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by LSilva on 04/08/2014.
 */
public class Note {
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

    public String getFormattedLastModification() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(this.lastModification);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar lastMod = Calendar.getInstance();
        lastMod.setTime(date);

        Calendar now = Calendar.getInstance();

        String returnDate;

        if (lastMod.get(Calendar.DATE) == now.get(Calendar.DATE) &&
                lastMod.get(Calendar.MONTH) == now.get(Calendar.MONTH) &&
                lastMod.get(Calendar.YEAR) == now.get(Calendar.YEAR)){
            formatter = new SimpleDateFormat("HH:mm:ss");
            returnDate = formatter.format(lastMod);
        } else if(lastMod.get(Calendar.WEEK_OF_YEAR) == now.get(Calendar.WEEK_OF_YEAR) &&
                  lastMod.get(Calendar.YEAR) == now.get(Calendar.YEAR)){
            formatter = new SimpleDateFormat("EEEEEEEEEE");
            returnDate = formatter.format(lastMod);
        } else {
            formatter = new SimpleDateFormat("yyyy/MM/dd");
            returnDate = formatter.format(lastMod);
        }

        return returnDate;
    }
}
