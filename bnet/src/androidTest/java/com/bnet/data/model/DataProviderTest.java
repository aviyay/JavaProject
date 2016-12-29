package com.bnet.data.model;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.bnet.shared.model.Constants;
import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.backend.ProvidableRepository;
import com.bnet.shared.model.services.utils.CursorUtils;
import com.bnet.data.model.backend.RepositoriesFactory;
import com.bnet.shared.model.services.utils.ProvidableUtils;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.entities.EntitiesSamples;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataProviderTest {
    private DataProvider provider;

    private static final String URI_PREFIX = "content://" + Constants.PROVIDER_AUTHORITY + "/";

    @Before
    public void setUp() throws Exception {
        provider = new DataProvider();
        RepositoriesFactory.getActivitiesRepository().reset();
        RepositoriesFactory.getBusinessesRepository().reset();
    }

    @Test
    public void insertActivity() throws Exception {
        Activity activity = EntitiesSamples.getActivity();
        ContentValues values = ProvidableUtils.contentValuesConvert(activity);
        Uri result = provider.insert(Uri.parse(URI_PREFIX + Constants.ACTIVITIES_URI_PATH), values);

        assertEquals(0, ContentUris.parseId(result));
    }

    @Test
    public void insertBusiness() throws Exception {
        Business business = EntitiesSamples.getBusiness();
        ContentValues values = ProvidableUtils.contentValuesConvert(business);
        Uri result = provider.insert(Uri.parse(URI_PREFIX + Constants.BUSINESSES_URI_PATH), values);

        assertEquals(0, ContentUris.parseId(result));
    }

    @Test
    public void queryActivity() throws Exception {
        Activity activity = EntitiesSamples.getActivity();
        ProvidableUtils.getRepository(activity).addAndReturnAssignedId(activity);

        Cursor cursor = provider.query(Uri.parse(URI_PREFIX + Constants.ACTIVITIES_URI_PATH), null, null, null, null);
        assertSingle(cursor, activity);
    }

    @Test
    public void queryBusiness() throws Exception {
        Business business = EntitiesSamples.getBusiness();
        ProvidableUtils.getRepository(business).addAndReturnAssignedId(business);

        Cursor cursor = provider.query(Uri.parse(URI_PREFIX + Constants.BUSINESSES_URI_PATH), null, null, null, null);
        assertSingle(cursor, business);
    }

    @Test
    public void querySingleRow() throws Exception {
        int id;
        Business business = EntitiesSamples.getBusiness();
        Business business2 = EntitiesSamples.getBusiness2();
        ProvidableUtils.getRepository(business).addAndReturnAssignedId(business);
        id = ProvidableUtils.getRepository(business).addAndReturnAssignedId(business2);

        Cursor cursor = provider.query(Uri.parse(URI_PREFIX + Constants.BUSINESSES_URI_PATH + "/" + id), null, null, null, null);

        assertSingle(cursor, business2);
    }

    @Test
    public void queryNews() throws Exception {
        Business business = EntitiesSamples.getBusiness();
        Business business2 = EntitiesSamples.getBusiness2();
        ProvidableRepository repository = ProvidableUtils.getRepository(business);
        repository.addAndReturnAssignedId(business);
        repository.getAll();
        repository.addAndReturnAssignedId(business2);

        assertTrue(repository.isSomethingNew());

        Cursor cursor = provider.query(Uri.parse(URI_PREFIX + Constants.BUSINESSES_URI_PATH), null, "news", null, null);

        assertSingle(cursor, business2);
    }

    private void assertSingle(Cursor cursor, Providable expected) {
        assertNotNull(cursor);
        cursor.moveToFirst();

        Providable result = CursorUtils.fromMatrixRow(expected, cursor);

        assertEquals(expected, result);
    }
}