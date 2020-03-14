package com.afterpay.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Validate timestamp format
 */
@Component
public class TimestampValidator {

    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat dayFormat;

    public TimestampValidator() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dayFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    public SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    public SimpleDateFormat getDayFormat() {
        return dayFormat;
    }

    public boolean isValidTimestamp(String timestamp) {

        if (StringUtils.isBlank(timestamp)) {
            return false;
        }

        try {
            simpleDateFormat.parse(timestamp);
        } catch (ParseException pe) {
            return false;
        }

        return true;
    }

    public boolean isTimeInPast(String timestamp) {

        if (isValidTimestamp(timestamp)) {
            try {
                Date date = simpleDateFormat.parse(timestamp);
                return !(date.after(new Date()));
            } catch (ParseException pe) {
                return false;
            }
        }

        return false;
    }

    public boolean isTimestampForDay(String timestamp, Date day) {
        try {
            if (dayFormat.parse(timestamp).compareTo(day) == 0) {
                return true;
            }
        } catch (ParseException pe) {
            return false;
        }

        return false;
    }

}
