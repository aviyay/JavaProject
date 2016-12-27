package com.bnet.tnet.model;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
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
    public void onReceive(Context context, Intent intent) {
        Log.d("MyCustomTag", "TNet: BroadCast received");
        refresh(context);
    }

    public static void refresh(Context context) {
        final String NEWS = "news";
        pull(context, NEWS);
    }

    private static ArrayList<Integer> findMissingAgencies(List<Activity> travels) {
        ArrayList<Integer> result = new ArrayList<>();

        List<Business> allAgencies = RepositoriesFactory.getBusinessesRepository().getAll();

        boolean found;
        for (Activity travel : travels) {
            found = false;

            for (Business agency : allAgencies)
                if (agency.getId() == travel.getBusinessId()) {
                    found = true;
                    break;
                }

            if (!found)
                result.add(travel.getBusinessId());
        }

        return result;
    }

    public static void freshStart(Context context) {
        resetRepositories();
        pull(context, null);
    }

    private static void resetRepositories() {
        ProvidableUtils.getRepository(Activity.class).reset();
        ProvidableUtils.getRepository(Business.class).reset();
    }

    private static void pull(Context context, String activityQuerySelection) {
        ContentResolver resolver = context.getContentResolver();

        Cursor cursor = runQuery(resolver, activityUri, activityQuerySelection);
        List<Activity> travels = filterTravels(cursor);

        ArrayList<Integer> agenciesToPull = findMissingAgencies(travels);
        ArrayList<Cursor> agenciesRows = new ArrayList<>();

        for (int id : agenciesToPull)
            agenciesRows.add(runQuery(resolver, getAgencyUri(id)));

        List<Business> agencies = convertAgencies(agenciesRows);

        fillRepositories(travels, agencies);
    }

    private static Cursor runQuery(ContentResolver resolver, Uri uri) {
        return runQuery(resolver, uri, null);
    }

    private static Cursor runQuery(ContentResolver resolver, Uri uri, String selection) {
        return resolver.query(uri, null, selection, null, null);
    }

    @NonNull
    private static List<Activity> filterTravels(Cursor cursor) {

        List<Activity> travels = new ArrayList<>();

        for (Activity activity : CursorUtils.cursorToProvidableList(new Activity(), cursor))
            if (activity.getType() == ActivityType.TRAVEL)
                travels.add(activity);

        return travels;
    }

    private static Uri getAgencyUri(int id) {
        return ContentUris.withAppendedId(businessUri, id);
    }

    private static List<Business> convertAgencies(List<Cursor> agenciesRows) {
        if (agenciesRows.size() == 0)
            return new ArrayList<>();

        Cursor mergedCursors = CursorUtils.mergeCursors(agenciesRows);

        return CursorUtils.cursorToProvidableList(new Business(), mergedCursors);
    }

    private static void fillRepositories(List<Activity> travels, List<Business> agencies) {
        for (Activity travel : travels)
            ProvidableUtils.getRepository(Activity.class).addAndReturnAssignedId(travel);
        for (Business agency : agencies)
            ProvidableUtils.getRepository(Business.class).addAndReturnAssignedId(agency);
    }
}
