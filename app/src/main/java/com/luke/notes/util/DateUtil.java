package com.luke.notes.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lalbuquerque on 8/12/14.
 */
public class DateUtil {

    public static String getFormattedLastModification(String lastModification) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(lastModification);
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
