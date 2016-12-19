package com.bnet.tnet.model;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.test.mock.MockContentProvider;
import android.test.mock.MockContentResolver;
import android.test.mock.MockContext;

import com.bnet.shared.model.CursorUtils;
import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.backend.ProvidableRepository;
import com.bnet.shared.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UpdatesReceiverTest {
    UpdatesReceiver updatesReceiver;
    TestContext context;

    private class TestContext extends MockContext
    {
        MockContentResolver resolver;
        List<Activity> activities;
        List<Business> businesses;

        public TestContext(List<Activity> activities, List<Business> businesses) {
            this.activities = activities;
            this.businesses = businesses;
            resolver = new MockContentResolver();
            resolver.addProvider(UpdatesReceiver.PROVIDER_URI, new StubProvider());
        }

        @Override
        public ContentResolver getContentResolver() {
            return resolver;
        }

        private class StubProvider extends MockContentProvider {
            @Override
            public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
                List<Providable> providables = getAppropriateProvidables(uri);

                MatrixCursor matrixCursor = new MatrixCursor(CursorUtils.getMatrixColumns(providables.get(0)));

                for (Providable p : providables)
                    matrixCursor.addRow(CursorUtils.ProvidableToObjectArray(p));

                return matrixCursor;
            }

            private List<Providable> getAppropriateProvidables(Uri uri) {
                return null;    //TODO: implement this
            }
        }
    }

    @Before
    public void setUp() throws Exception {
        updatesReceiver = new UpdatesReceiver();
    }

    private static final String UPDATE_ACTION = "com.bnet.action.UPDATE";   //TODO: Move shared constant to Shared module

    @Test
    public void testUpdateOneActivity() throws Exception {
        List<Activity> activities = new ArrayList<>();
        Activity activity = new Activity();
        activities.add(activity);

        context = new TestContext(activities, new ArrayList<Business>());

        updatesReceiver.onReceive(context, new Intent(UPDATE_ACTION));

        List<Activity> repoActivities = RepositoriesFactory.getActivitiesRepository().getAll();

        assertEquals(1, repoActivities.size());
        assertEquals(activity, repoActivities.get(0));
    }
}