package com.bnet.data.model;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.bnet.shared.model.ContentValuesConverter;
import com.bnet.shared.model.CursorUtils;
import com.bnet.shared.model.backend.Providable;
import com.bnet.data.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.entities.EntitiesSamples;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataProviderTest {
    private DataProvider provider;

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
        assertNotNull(cursor);
        cursor.moveToFirst();

        Activity result = CursorUtils.fromMatrixRow(activity, cursor);

        assertEquals(activity, result);
    }

    @Test
    public void queryBusiness() throws Exception {
        Business business = EntitiesSamples.getBusiness();
        business.getRepository().addAndReturnAssignedId(business);

        Cursor cursor = provider.query(Uri.parse(URI_PREFIX + BUSINESSES_POSTFIX), null, null, null, null);
        assertNotNull(cursor);
        cursor.moveToFirst();

        Business result = CursorUtils.fromMatrixRow(business, cursor);

        assertEquals(business, result);
    }
}