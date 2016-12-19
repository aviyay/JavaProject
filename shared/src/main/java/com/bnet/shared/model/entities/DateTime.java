package com.bnet.shared.model.entities;

import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTime {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

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
        return String.format(Locale.US, "%d:%d %s/%s/%s", getHour(), getMinute(), getDay(), getMonth(), getYear());
    }

    public static DateTime parse (String formattedDateTime) throws ParseException{
        DateTime result = new DateTime();
        Pattern pattern = Pattern.compile("(\\d+):(\\d+) (\\d+)/(\\d+)/(\\d+)");
        Matcher matcher = pattern.matcher(formattedDateTime);

        try {
            matcher.matches();

            result.setYear(Integer.parseInt(matcher.group(5)));
            result.setMonth(Integer.parseInt(matcher.group(4)));
            result.setDay(Integer.parseInt(matcher.group(3)));
            result.setHour(Integer.parseInt(matcher.group(1)));
            result.setMinute(Integer.parseInt(matcher.group(2)));

            return result;
        }
        catch (Exception e) {
            throw new ParseException("Invalid input, should be in the pattern hh:hh dd/MM/yyyy", 0);
        }
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof DateTime))
            return false;

        DateTime other = (DateTime)obj;

        return format().equals(other.format());
    }

    @Override
    public int hashCode() {
        return format().hashCode();
    }
}
