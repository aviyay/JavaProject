package com.bnet.shared.model.entities;

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

    public  void setMinute(int minute) {
        this.minute = minute;
    }

    public static DateTime parse (String formattedDateTime) {
        DateTime result = new DateTime();
        Pattern pattern = Pattern.compile("(\\d+):(\\d+) (\\d+)/(\\d+)/(\\d+)");
        Matcher matcher = pattern.matcher(formattedDateTime);

        try {
            if (!matcher.matches())
                throw new Exception("invalid format");

            result.setHour(Integer.parseInt(matcher.group(1)));
            result.setMinute(Integer.parseInt(matcher.group(2)));

            result.setYear(Integer.parseInt(matcher.group(5)));
            result.setMonth(Integer.parseInt(matcher.group(4)));
            result.setDay(Integer.parseInt(matcher.group(3)));

            return result;
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid input, should be in the pattern hh:hh dd/MM/yyyy");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateTime dateTime = (DateTime) o;

        return year == dateTime.year
                && month == dateTime.month
                && day == dateTime.day
                && hour == dateTime.hour
                && minute == dateTime.minute;
    }

    @Override
    public int hashCode() {
        int result = year;
        result = 31 * result + month;
        result = 31 * result + day;
        result = 31 * result + hour;
        result = 31 * result + minute;
        return result;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s %s", toHourString(), toDateString());
    }

    public String toDateString() {
        return String.format(Locale.US, "%02d/%02d/%02d", getDay(), getMonth(), getYear());
    }
    public String toHourString() {
        return String.format(Locale.US, "%02d:%02d",  getHour(), getMinute());
    }
}
