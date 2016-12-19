package com.bnet.data.model;

import android.content.UriMatcher;
import android.net.Uri;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtiMatcherTest {
    UriMatcher uriMatcher;

    private static final int ACTIVITIES = 1;
    private static final int BUSINESSES = 2;

    @Before
    public void setUp() throws Exception {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(DataProvider.AUTHORITY, "activities", ACTIVITIES);
        uriMatcher.addURI(DataProvider.AUTHORITY, "businesses", BUSINESSES);
    }

    @Test
    public void testMatchActivities() throws Exception {
        Uri uri = Uri.parse("content://"+DataProvider.AUTHORITY + "/activities");

        assertEquals(ACTIVITIES, uriMatcher.match(uri));
    }

    @Test
    public void testMatchBusinesses() throws Exception {
        Uri uri = Uri.parse("content://"+DataProvider.AUTHORITY + "/businesses");

        assertEquals(BUSINESSES, uriMatcher.match(uri));
    }

}