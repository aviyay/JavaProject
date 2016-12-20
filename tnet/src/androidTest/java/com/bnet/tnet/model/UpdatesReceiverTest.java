package com.bnet.tnet.model;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.test.mock.MockContentProvider;
import android.test.mock.MockContentResolver;
import android.test.mock.MockContext;

import com.bnet.shared.model.CursorUtils;
import com.bnet.shared.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UpdatesReceiverTest {
    UpdatesReceiver updatesReceiver;
    TestContext context;
    MatrixCursor businesses;
    MatrixCursor activities;

    private class TestContext extends MockContext {
        MockContentResolver resolver;
        private ContentProvider provider;

        public TestContext() {

            provider = new StubQueryProvider();
            resolver = new MockContentResolver();
            resolver.addProvider(UpdatesReceiver.PROVIDER_URI, provider);
        }

        @Override
        public ContentResolver getContentResolver() {
            return resolver;
        }

        private class StubQueryProvider extends MockContentProvider {
            @Override
            public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
                String path = uri.getPath();
                if (path.equals(UpdatesReceiver.ACTIVITIES_POSTFIX))
                    return activities;

                if (path.equals(UpdatesReceiver.Businesses_POSTFIX))
                    return businesses;

                return null;
            }
        }
    }

    @Before
    public void setUp() throws Exception {
        updatesReceiver = new UpdatesReceiver();
        RepositoriesFactory.getActivitiesRepository().reset();
        RepositoriesFactory.getBusinessesRepository().reset();
        initializeCursors();
    }

    private void initializeCursors() {
        businesses = new MatrixCursor(CursorUtils.getMatrixColumns(new Business()));
        activities = new MatrixCursor(CursorUtils.getMatrixColumns(new Activity()));
    }

    private static final String UPDATE_ACTION = "com.bnet.action.UPDATE";   //TODO: Move shared constant to Shared module

    @Test
    public void testUpdateOneActivity() throws Exception {
        List<Activity> repoActivities = RepositoriesFactory.getActivitiesRepository().getAll();
        assertEquals(0, repoActivities.size());

        Activity activity = new Activity();
        activities.addRow(CursorUtils.ProvidableToObjectArray(activity));

        context = new TestContext();

        updatesReceiver.onReceive(context, new Intent(UPDATE_ACTION));

        repoActivities = RepositoriesFactory.getActivitiesRepository().getAll();
        assertEquals(1, repoActivities.size());
        assertEquals(activity, repoActivities.get(0));
    }

    @Test
    public void testUpdateOneBusiness() throws Exception {
        List<Business> repoBusinesses = RepositoriesFactory.getBusinessesRepository().getAll();
        assertEquals(0, repoBusinesses.size());

        Business business = new Business();
        businesses.addRow(CursorUtils.ProvidableToObjectArray(business));

        context = new TestContext();

        updatesReceiver.onReceive(context, new Intent(UPDATE_ACTION));

        repoBusinesses = RepositoriesFactory.getBusinessesRepository().getAll();
        assertEquals(1, repoBusinesses.size());
        assertEquals(business, repoBusinesses.get(0));
    }
}