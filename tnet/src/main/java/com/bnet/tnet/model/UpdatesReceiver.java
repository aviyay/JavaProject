package com.bnet.tnet.model;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bnet.shared.model.Constants;
import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.ActivityType;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.services.utils.CursorUtils;
import com.bnet.shared.model.services.utils.ProvidableUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public static void freshStart(Context context) {
        resetRepositories();
        pull(context, null);
    }

    private static void resetRepositories() {
        ProvidableUtils.getRepository(Activity.class).reset();
        ProvidableUtils.getRepository(Business.class).reset();
    }

    private static void pull(final Context context,final String activityQuerySelection) {
        new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                ContentResolver resolver = context.getContentResolver();

                Cursor cursor = runQuery(resolver, activityUri, activityQuerySelection);
                List<Activity> travels = filterTravels(cursor);

                ArrayList<Long> agenciesToPull = findMissingAgencies(travels);
                ArrayList<Cursor> agenciesRows = new ArrayList<>();

                for (long id : agenciesToPull)
                    agenciesRows.add(runQuery(resolver, getAgencyUri(id)));

                List<Business> agencies = convertAgencies(agenciesRows);

                fillRepositories(travels, agencies);
                return  null;
            }
        }.execute();
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

        for (Activity activity : CursorUtils.cursorToProvidableList(Activity.class, cursor))
            if (activity.getType() == ActivityType.TRAVEL)
                travels.add(activity);

        return travels;
    }

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

            if (!found&&!result.contains(travel.getBusinessId()))
                result.add(travel.getBusinessId());
        }

        return result;
    }

    private static Uri getAgencyUri(long id) {
        return ContentUris.withAppendedId(businessUri, id);
    }

    private static List<Business> convertAgencies(List<Cursor> agenciesRows) {
        if (agenciesRows.size() == 0)
            return new ArrayList<>();

        Cursor mergedCursors = CursorUtils.mergeCursors(agenciesRows);

        return CursorUtils.cursorToProvidableList(Business.class, mergedCursors);
    }

    private static void fillRepositories(List<Activity> travels, List<Business> agencies) {
        new AsyncTask<List,Void,Void>()
        {
            @Override
            protected Void doInBackground(List... params) {
                for (Object travel : params[0])
                    ProvidableUtils.getRepository(Activity.class).addAndReturnAssignedId((Activity)travel);
                for (Object agency : params[1])
                    ProvidableUtils.getRepository(Business.class).addAndReturnAssignedId((Business)agency);
                return null;
            }
        }.execute(travels,agencies);
    }
}
