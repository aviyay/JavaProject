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

    /**
     * Get Year
     * @return The Year in the time
     */
    public int getYear() {
        return year;
    }
    /**
     * Set Year
     * @param year The Year to be set
     */
    public void setYear(int year) {
        this.year = year;
    }
    /**
     * Get Month
     * @return The Month in the time
     */
    public int getMonth() {
        return month;
    }
    /**
     * Set Month
     * @param month The Month to be set
     */
    public void setMonth(int month) {
        this.month = month;
    }
    /**
     * Get Day
     * @return The Day in the time
     */
    public int getDay() {
        return day;
    }
    /**
     * Set Day
     * @param day The Day to be set
     */
    public void setDay(int day) {
        this.day = day;
    }
    /**
     * Get Hour
     * @return The Hour in the time
     */
    public int getHour() {
        return hour;
    }
    /**
     * Set Hour
     * @param hour The Hour to be set
     */
    public void setHour(int hour) {
        this.hour = hour;
    }
    /**
     * Get Minute
     * @return The Minute in the time
     */
    public int getMinute() {
        return minute;
    }
    /**
     * Set Minute
     * @param minute The Minute to be set
     */
    public  void setMinute(int minute) {
        this.minute = minute;
    }

    /**
     * Parse String of formatted DateTime into a new DateTime variable
     * @param formattedDateTime the formatted string that reparent a DateTime
     * @return The new DateTime that was created from the given string
     */
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

    /**
     * Get String that represent the Date in the DateTime
     * @return String of the date in the DateTime
     */
    public String toDateString() {
        return String.format(Locale.US, "%02d/%02d/%02d", getDay(), getMonth(), getYear());
    }
    /**
     * Get String that represent the Hour in the DateTime
     * @return String of the hour in the DateTime
     */
    public String toHourString() {
        return String.format(Locale.US, "%02d:%02d",  getHour(), getMinute());
    }
}
