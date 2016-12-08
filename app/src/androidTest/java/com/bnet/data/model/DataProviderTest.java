package com.bnet.data.model;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import com.bnet.data.model.entities.Activity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataProviderTest {
    DataProvider provider;

    private static final String URI_PREFIX = "content://" + DataProvider.AUTHORITY + "/";
    private static final String ACTIVITIES_POSTFIX = "activities";
    private static final String BUSINESSES_POSTFIX = "businesses";
    @Before
    public void setUp() throws Exception {
        provider = new DataProvider();
    }

    @Test
    public void GetType() throws Exception {
        getTypeTestHelper(ACTIVITIES_POSTFIX);
        getTypeTestHelper(BUSINESSES_POSTFIX);
    }

    private void getTypeTestHelper(String entityType) {
        Uri uri = Uri.parse(URI_PREFIX + entityType);

        String expected = "vnd.android.cursor.dir/vnd.com.bnet.provider." + entityType;
        String result = provider.getType(uri);

        assertEquals(expected, result);
    }

    @Test
    public void insertActivity() throws Exception {
        Activity activity = EntitiesSamples.getActivity();
        ContentValues values = ContentValuesConverter.activityToContentValues(activity);
        Uri result = provider.insert(Uri.parse(URI_PREFIX + ACTIVITIES_POSTFIX), values);

        assertEquals(2, ContentUris.parseId(result));
    }

    @Test
    public void query() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

}