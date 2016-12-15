package com.bnet.data.model;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import com.bnet.data.model.entities.Activity;
import com.bnet.data.model.entities.Business;
import com.bnet.data.model.entities.EntitiesSamples;

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
    public void insertActivity() throws Exception {
        Activity activity = EntitiesSamples.getActivity();
        ContentValues values = ContentValuesConverter.activityToContentValues(activity);
        Uri result = provider.insert(Uri.parse(URI_PREFIX + ACTIVITIES_POSTFIX), values);

        assertEquals(0, ContentUris.parseId(result));
    }

    @Test
    public void insertBusiness() throws Exception {
        Business business = EntitiesSamples.getBusiness();
        ContentValues values = ContentValuesConverter.businessToContentValues(business);
        Uri result = provider.insert(Uri.parse(URI_PREFIX + BUSINESSES_POSTFIX), values);

        assertEquals(0, ContentUris.parseId(result));
    }

    @Test
    public void query() throws Exception {

    }
}