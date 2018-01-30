package com.chillbox.app.helper;

import com.chillbox.app.view.utils.TimeUtils;

import javax.inject.Inject;

/**
 * Created by aman1 on 01/01/2018.
 */

public class DateHelper {

    @Inject
    public DateHelper() {
    }

    public String convertTime(String time) {
        return TimeUtils.convertTime(time);
    }
}
