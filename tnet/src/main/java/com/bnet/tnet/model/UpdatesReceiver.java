package com.bnet.tnet.model;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.bnet.shared.model.Constants;
import com.bnet.shared.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.ActivityType;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.services.utils.CursorUtils;
import com.bnet.shared.model.services.utils.ProvidableUtils;

import java.util.ArrayList;
import java.util.List;

public class UpdatesReceiver extends BroadcastReceiver {

    private static final String baseUriString = "content://" + Constants.PROVIDER_AUTHORITY + "/";
    private static final Uri activityUri =
            Uri.parse(baseUriString + ProvidableUtils.getURIPath(Activity.class));
    private static final Uri businessUri =
            Uri.parse(baseUriString + ProvidableUtils.getURIPath(Business.class));

    public UpdatesReceiver() {
        Log.d("MyCustomTag", "TNet: UpdatesReceiverCreated");
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.d("MyCustomTag", "TNet: BroadCast received");
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                refresh(context);
                return null;
            }
        }.execute();
    }

    /**
     * Refresh the database, and pull new data if there is any
     * @param context The context of the application
     */
    public static void refresh(Context context) {
        final String NEWS = "news";
        pull(context, NEWS);
    }

    /**
     * Clean the database and pull the current data
     * @param context The context of the application
     */
    public static void freshStart(Context context) {
        resetRepositories();
        pull(context, null);
    }

    /**
     * Reset local repositories
     */
    private static void resetRepositories() {
        ProvidableUtils.getRepository(Activity.class).reset();
        ProvidableUtils.getRepository(Business.class).reset();
    }

    private static void pull(final Context context, String activityQuerySelection) {
        ContentResolver resolver = context.getContentResolver();

        Cursor cursor = runQuery(resolver, activityUri, activityQuerySelection);
        List<Activity> travels = filterTravels(cursor);

        ArrayList<Long> agenciesToPull = findMissingAgencies(travels);
        ArrayList<Cursor> agenciesRows = new ArrayList<>();

        for (long id : agenciesToPull)
            agenciesRows.add(runQuery(resolver, getAgencyUri(id)));

        List<Business> agencies = convertAgencies(agenciesRows);

        fillRepositories(travels, agencies);
    }

    /**
     * Send query request to the Content Provider
     * @param resolver The resolver for the content provider
     * @param uri The URI of the query
     * @return Thr result of the query
     */
    private static Cursor runQuery(ContentResolver resolver, Uri uri) {
        return runQuery(resolver, uri, null);
    }

    /**
     * Send query request to the Content Provider
     * @param resolver The resolver for the content provider
     * @param uri The URI of the query
     * @param selection The query type. e.g: "news"
     * @return Thr result of the query
     */
    private static Cursor runQuery(ContentResolver resolver, Uri uri, String selection) {
        return resolver.query(uri, null, selection, null, null);
    }

    /**
     * Filter the travel activities from the all the activities
     * @param cursor The cursor of all of the activities
     * @return The filtered list of travel activities
     */
    private static List<Activity> filterTravels(Cursor cursor) {

        List<Activity> travels = new ArrayList<>();

        for (Activity activity : CursorUtils.cursorToProvidableList(Activity.class, cursor))
            if (activity.getType() == ActivityType.TRAVEL)
                travels.add(activity);

        return travels;
    }

    /**
     * Find all the agencies that have travels in the given list
     * @param travels The list of travels
     * @return The list of the found agencies
     */
    private static ArrayList<Long> findMissingAgencies(List<Activity> travels) {
        ArrayList<Long> result = new ArrayList<>();

        List<Business> allAgencies = RepositoriesFactory.getBusinessesRepository().getAll();

        boolean found;
        for (Activity travel : travels) {
            found = false;

            for (Business agency : allAgencies)
                if (agency.getId() == travel.getBusinessId()) {
                    found = true;
                    break;
                }

            if (!found && !result.contains(travel.getBusinessId()))
                result.add(travel.getBusinessId());
        }

        return result;
    }

    /**
     * Get the URI of a specific agency
     * @param id The id of the specific agency
     * @return THr URI of the specific agency
     */
    private static Uri getAgencyUri(long id) {
        return ContentUris.withAppendedId(businessUri, id);
    }

    /**
     * Convert Businesses Cursor into a list
     * @param agenciesRows The Business Cursor
     * @return The converted list of businesses
     */
    private static List<Business> convertAgencies(List<Cursor> agenciesRows) {
        if (agenciesRows.size() == 0)
            return new ArrayList<>();

        Cursor mergedCursors = CursorUtils.mergeCursors(agenciesRows);

        return CursorUtils.cursorToProvidableList(Business.class, mergedCursors);
    }

    /**
     * Fill the local repositories with the lists
     * @param travels The travels list to put in the local repositories
     * @param agencies The agencies list to put in the local repositories
     */
    private static void fillRepositories(List<Activity> travels, List<Business> agencies) {
        long original_id;
        for (Business agency : agencies){
            original_id = agency.getId();
            ProvidableUtils.getRepository(Business.class).addAndReturnAssignedId(agency);
            agency.setId(original_id);
        }

        for (Activity travel : travels)
            ProvidableUtils.getRepository(Activity.class).addAndReturnAssignedId(travel);
    }
}
