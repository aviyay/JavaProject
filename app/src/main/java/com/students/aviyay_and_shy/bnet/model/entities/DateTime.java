package com.students.aviyay_and_shy.bnet.model.entities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTime {
    int year;
    int month;
    int day;
    int hour;
    int minute;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String format() {
        return String.format("%d:%d %s/%s/%s", getHour(), getMinute(), getDay(), getMonth(), getYear());
    }

    public static DateTime parse (String formattedDateTime) throws Exception{
        DateTime result = new DateTime();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(formattedDateTime);

        boolean resul = matcher.matches();

        int a = Integer.parseInt(matcher.group(1));
        return resul ? new DateTime() : null;

        /*result.setYear(Integer.parseInt(1));
        result.setMonth(Integer.parseInt(matcher.group(1)));
        result.setDay(Integer.parseInt(matcher.group(1)));
        result.setHour(Integer.parseInt(matcher.group(1)));
        result.setMinute(Integer.parseInt(matcher.group(1)));

        return result;*/
    }
}
