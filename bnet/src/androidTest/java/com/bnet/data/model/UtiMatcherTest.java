package com.bnet.data.model;

import android.content.UriMatcher;
import android.net.Uri;

import com.bnet.shared.model.Constants;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtiMatcherTest {
    private UriMatcher uriMatcher;

    private static final int ACTIVITIES = 1;
    private static final int BUSINESSES = 2;
    private static final String basePath = "content://" + Constants.PROVIDER_AUTHORITY + "/";

    @Before
    public void setUp() throws Exception {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Constants.PROVIDER_AUTHORITY, Constants.ACTIVITIES_URI_PATH, ACTIVITIES);
        uriMatcher.addURI(Constants.PROVIDER_AUTHORITY, Constants.BUSINESSES_URI_PATH, BUSINESSES);
    }

    @Test
    public void testMatchActivities() throws Exception {
        Uri uri = Uri.parse(basePath + Constants.ACTIVITIES_URI_PATH);

        assertEquals(ACTIVITIES, uriMatcher.match(uri));
    }

    @Test
    public void testMatchBusinesses() throws Exception {
        Uri uri = Uri.parse(basePath + Constants.BUSINESSES_URI_PATH);

        assertEquals(BUSINESSES, uriMatcher.match(uri));
    }

}