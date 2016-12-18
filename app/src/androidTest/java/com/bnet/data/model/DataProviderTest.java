package com.bnet.data.model;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.bnet.data.model.backend.Providable;
import com.bnet.data.model.backend.RepositoriesFactory;
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
        RepositoriesFactory.getActivitiesRepository().reset();
        RepositoriesFactory.getBusinessesRepository().reset();
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
    public void queryActivity() throws Exception {
        Activity activity = EntitiesSamples.getActivity();
        activity.getRepository().addAndReturnAssignedId(activity);

        Cursor cursor = provider.query(Uri.parse(URI_PREFIX + ACTIVITIES_POSTFIX), null, null, null, null);
        cursor.moveToFirst();

        Activity result = (Activity) fromMatrixRow(activity, cursor);

        assertEquals(activity, result);
    }

    @Test
    public void queryBusiness() throws Exception {
        Business business = EntitiesSamples.getBusiness();
        business.getRepository().addAndReturnAssignedId(business);

        Cursor cursor = provider.query(Uri.parse(URI_PREFIX + BUSINESSES_POSTFIX), null, null, null, null);
        cursor.moveToFirst();

        Business result = (Business) fromMatrixRow(business, cursor);

        assertEquals(business, result);
    }
    public Providable fromMatrixRow(Providable match, Cursor cursor) throws Exception {
        ContentValues values = new ContentValues();

        for (int i = 0; i < cursor.getColumnCount(); i++)
            values.put(cursor.getColumnName(i), cursor.getString(i));

        return match.fromContentValues(values);
    }
}