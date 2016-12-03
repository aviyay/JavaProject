package com.students.aviyay_and_shy.bnet.model.entities;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class DateTimeTest {

    @Test
    public void format() throws Exception {
        DateTime dateTime = new DateTime();
        dateTime.setYear(2016);
        dateTime.setMonth(8);
        dateTime.setDay(4);
        dateTime.setHour(21);
        dateTime.setMinute(30);

        String result = dateTime.format();
        assertEquals("21:30 4/8/2016",result);
    }

    @Test
    public void parse() throws Exception {
        DateTime dateTime = DateTime.parse("21");

        assertNotNull(dateTime);
        /*assertEquals(2016, dateTime.getYear());
        assertEquals(8, dateTime.getMonth());
        assertEquals(4, dateTime.getDay());
        assertEquals(21, dateTime.getHour());
        assertEquals(30, dateTime.getMinute());*/
    }

}