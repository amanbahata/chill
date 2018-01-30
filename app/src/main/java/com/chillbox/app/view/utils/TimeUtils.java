package com.chillbox.app.view.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by aman1 on 01/01/2018.
 */

public final class TimeUtils {

    private TimeUtils() {
        throw new IllegalStateException("Never instantiate a utility class.");
    }

    public static String convertTime(String time) {
        time = time.replaceAll("T", " ").trim()
                .replaceAll("Z", " ").trim()
                .split("\\.", 2)[0];
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
}
