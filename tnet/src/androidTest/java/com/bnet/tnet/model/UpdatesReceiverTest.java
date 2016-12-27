package com.bnet.tnet.model;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.test.mock.MockContentProvider;
import android.test.mock.MockContentResolver;
import android.test.mock.MockContext;

import com.bnet.shared.model.Constants;
import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.backend.ProvidableRepository;
import com.bnet.shared.model.backend.RepositoriesFactory;
import com.bnet.shared.model.datasource.ListProvidableRepository;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.entities.EntitiesSamples;
import com.bnet.shared.model.services.utils.CursorUtils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UpdatesReceiverTest {

    private ProvidableRepository<Activity> curRepoActivities = RepositoriesFactory.getActivitiesRepository();
    private ProvidableRepository<Business> curRepoBusinesses = RepositoriesFactory.getBusinessesRepository();

    private ProvidableRepository<Providable> ProviderRepoActivities;
    private ProvidableRepository<Providable> ProviderRepoBusinesses;

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
                int id = getIdOrMinusOne(uri);
                List<Providable> result;
                ProvidableRepository<Providable> match;
                String action = "";

                if (id != -1)
                    action = "id";

                if (selection == "news")
                    action = "news";

                if (path.startsWith("/" + Constants.ACTIVITIES_URI_PATH))
                    match = ProviderRepoActivities;
                else if (path.startsWith("/" + Constants.BUSINESSES_URI_PATH))
                    match = ProviderRepoBusinesses;
                else
                    throw new IllegalArgumentException("Invalid request");

                switch (action) {
                    case "id":
                        result = new ArrayList<>();
                        result.add(match.getAll().get(id));
                        break;
                    case "news":
                        result = match.getAllNews();
                        break;
                    default:
                        result = match.getAll();
                        break;
                }

                return CursorUtils.providableListToCursor(result);
            }

            private int getIdOrMinusOne(@NonNull Uri uri) {
                if (uri.getLastPathSegment().matches("^\\d+$"))
                    return (int) ContentUris.parseId(uri);
                return -1;
            }
        }
    }

    @Before
    public void setUp() throws Exception {
        curRepoActivities.reset();
        curRepoBusinesses.reset();

        ProviderRepoActivities = new ListProvidableRepository<>();
        ProviderRepoBusinesses = new ListProvidableRepository<>();
    }

    @Test
    public void oneAgencyWithOneTravel() throws Exception {
        Activity activity = EntitiesSamples.getActivity();
        Business business = EntitiesSamples.getBusiness();

        ProviderRepoActivities.addAndReturnAssignedId(activity);
        ProviderRepoBusinesses.addAndReturnAssignedId(business);

        runFreshStart();

        assertEquals(1, curRepoActivities.getAll().size());
        assertEquals(1, curRepoBusinesses.getAll().size());

        assertEquals(activity, curRepoActivities.getAll().get(0));
        assertEquals(business, curRepoBusinesses.getAll().get(0));
    }

    @Test
    public void oneNonAgencyWithOneNonTravel() throws Exception {
        Activity activity = EntitiesSamples.getNonTravelActivity();
        Business business = EntitiesSamples.getNonAgencyBusiness();

        ProviderRepoActivities.addAndReturnAssignedId(activity);
        ProviderRepoBusinesses.addAndReturnAssignedId(business);

        runFreshStart();

        assertEquals(0, curRepoActivities.getAll().size());
        assertEquals(0, curRepoBusinesses.getAll().size());
    }

    @Test
    public void mixedAgencyAndTravelWithNonAgencyAndNonTravel() throws Exception {
        Activity activity1 = EntitiesSamples.getActivity();
        Activity activity2 = EntitiesSamples.getNonTravelActivity();
        Business business1 = EntitiesSamples.getBusiness();
        Business business2 = EntitiesSamples.getNonAgencyBusiness();

        ProviderRepoActivities.addAndReturnAssignedId(activity1);
        ProviderRepoActivities.addAndReturnAssignedId(activity2);
        ProviderRepoBusinesses.addAndReturnAssignedId(business1);
        ProviderRepoBusinesses.addAndReturnAssignedId(business2);

        runFreshStart();

        assertEquals(1, curRepoActivities.getAll().size());
        assertEquals(1, curRepoBusinesses.getAll().size());

        assertEquals(activity1, curRepoActivities.getAll().get(0));
        assertEquals(business1, curRepoBusinesses.getAll().get(0));
    }

    @Test
    public void addOneNonTravelAndRefresh() throws Exception {
        Activity activity = EntitiesSamples.getActivity();
        Activity activity2 = EntitiesSamples.getNonTravelActivity();
        Business business = EntitiesSamples.getBusiness();

        ProviderRepoActivities.addAndReturnAssignedId(activity);
        ProviderRepoBusinesses.addAndReturnAssignedId(business);

        runFreshStart();

        activity2.setBusinessId(business.getId());
        ProviderRepoActivities.addAndReturnAssignedId(activity2);

        runRefresh();

        assertEquals(1, curRepoActivities.getAll().size());
        assertEquals(1, curRepoBusinesses.getAll().size());

        assertTrue(curRepoActivities.getAll().contains(activity));
        assertEquals(business, curRepoBusinesses.getAll().get(0));
    }

    @Test
    public void addOneTravelThatWeAlreadyHaveItsAgencyAndRefresh() throws Exception {
        Activity activity = EntitiesSamples.getActivity();
        Activity activity2 = EntitiesSamples.getActivity2();
        Business business = EntitiesSamples.getBusiness();

        ProviderRepoActivities.addAndReturnAssignedId(activity);
        ProviderRepoBusinesses.addAndReturnAssignedId(business);

        runFreshStart();

        activity2.setBusinessId(business.getId());
        ProviderRepoActivities.addAndReturnAssignedId(activity2);

        runRefresh();

        assertEquals(2, curRepoActivities.getAll().size());
        assertEquals(1, curRepoBusinesses.getAll().size());

        assertTrue(curRepoActivities.getAll().contains(activity));
        assertTrue(curRepoActivities.getAll().contains(activity2));
        assertEquals(business, curRepoBusinesses.getAll().get(0));
    }

    @Test
    public void addOneTravelThatWeDoNotHaveItsAgencyAndRefresh() throws Exception {
        Activity activity = EntitiesSamples.getActivity();
        Activity activity2 = EntitiesSamples.getActivity2();
        Business business = EntitiesSamples.getBusiness();
        Business business2 = EntitiesSamples.getBusiness2();

        ProviderRepoActivities.addAndReturnAssignedId(activity);
        ProviderRepoBusinesses.addAndReturnAssignedId(business);
        ProviderRepoBusinesses.addAndReturnAssignedId(business2);

        runFreshStart();

        assertEquals(1, curRepoBusinesses.getAll().size());

        ProviderRepoActivities.addAndReturnAssignedId(activity2);

        runRefresh();

        assertEquals(2, curRepoActivities.getAll().size());
        assertEquals(2, curRepoBusinesses.getAll().size());

        assertTrue(curRepoActivities.getAll().contains(activity));
        assertTrue(curRepoActivities.getAll().contains(activity2));
        assertTrue(curRepoBusinesses.getAll().contains(business));
        assertTrue(curRepoBusinesses.getAll().contains(business2));
    }

    @Test
    public void preserveDataEvenWhenBNetIsClosed() throws Exception {
        Activity activity = EntitiesSamples.getActivity();
        Business business = EntitiesSamples.getBusiness();

        ProviderRepoActivities.addAndReturnAssignedId(activity);
        ProviderRepoBusinesses.addAndReturnAssignedId(business);

        runFreshStart();

        ProviderRepoActivities.reset();
        ProviderRepoBusinesses.reset();

        runRefresh();

        assertEquals(1, curRepoActivities.getAll().size());
        assertEquals(1, curRepoBusinesses.getAll().size());

        assertEquals(activity, curRepoActivities.getAll().get(0));
        assertEquals(business, curRepoBusinesses.getAll().get(0));
    }

    private void runFreshStart() {
        UpdatesReceiver.freshStart(new TestContext());
    }

    private void runRefresh() {
        UpdatesReceiver.refresh(new TestContext());
    }

}