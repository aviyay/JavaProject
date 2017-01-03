package com.bnet.shared.model.entities;

import org.junit.Test;

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

        String result = dateTime.toString();
        assertEquals("21:30 04/08/2016",result);
    }

    @Test
    public void parseCorrect() throws Exception {
        DateTime dateTime = DateTime.parse("21:30 04/08/2016");

        assertEquals(2016, dateTime.getYear());
        assertEquals(8, dateTime.getMonth());
        assertEquals(4, dateTime.getDay());
        assertEquals(21, dateTime.getHour());
        assertEquals(30, dateTime.getMinute());
    }

    @Test
    public void parseIncorrect() throws Exception {

        try {
            DateTime.parse("Invalid Input");
            fail();
        }
        catch (IllegalArgumentException e) {
            assertEquals("Invalid input, should be in the pattern hh:hh dd/MM/yyyy", e.getMessage());
        }
    }
}