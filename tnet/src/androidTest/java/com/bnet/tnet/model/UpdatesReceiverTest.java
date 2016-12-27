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

import com.bnet.shared.model.Constants;
import com.bnet.shared.model.services.utils.CursorUtils;
import com.bnet.shared.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UpdatesReceiverTest {
    private UpdatesReceiver updatesReceiver;
    private TestContext context;
    private MatrixCursor businesses;
    private MatrixCursor activities;

    private class TestContext extends MockContext {
        MockContentResolver resolver;
        private ContentProvider provider;

        public TestContext() {

            provider = new StubQueryProvider();
            resolver = new MockContentResolver();
            resolver.addProvider(Constants.PROVIDER_AUTHORITY, provider);
        }

        @Override
        public ContentResolver getContentResolver() {
            return resolver;
        }

        private class StubQueryProvider extends MockContentProvider {
            @Override
            public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
                String path = uri.getPath();
                if (path.equals("/"+ Constants.ACTIVITIES_URI_PATH))
                    return activities;

                if (path.equals("/"+ Constants.BUSINESSES_URI_PATH))
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

    @Test
    public void testUpdateOneActivity() throws Exception {
        List<Activity> repoActivities = RepositoriesFactory.getActivitiesRepository().getAll();
        assertEquals(0, repoActivities.size());

        Activity activity = new Activity();
        activities.addRow(CursorUtils.providableToObjectArray(activity));

        context = new TestContext();

        updatesReceiver.onReceive(context, new Intent(Constants.UPDATE_ACTION));

        repoActivities = RepositoriesFactory.getActivitiesRepository().getAll();
        assertEquals(1, repoActivities.size());
        assertEquals(activity, repoActivities.get(0));
    }

    @Test
    public void testUpdateOneBusiness() throws Exception {
        List<Business> repoBusinesses = RepositoriesFactory.getBusinessesRepository().getAll();
        assertEquals(0, repoBusinesses.size());

        Business business = new Business();
        businesses.addRow(CursorUtils.providableToObjectArray(business));

        context = new TestContext();

        updatesReceiver.onReceive(context, new Intent(Constants.UPDATE_ACTION));

        repoBusinesses = RepositoriesFactory.getBusinessesRepository().getAll();
        assertEquals(1, repoBusinesses.size());
        assertEquals(business, repoBusinesses.get(0));
    }
}