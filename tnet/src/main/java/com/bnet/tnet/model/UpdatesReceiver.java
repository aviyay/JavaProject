package com.bnet.tnet.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.bnet.shared.model.CursorUtils;
import com.bnet.shared.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;

public class UpdatesReceiver extends BroadcastReceiver {
    public static final String PROVIDER_URI = "content://com.bnet.provider/";
    public static final String ACTIVITIES_POSTFIX = "activities";
    public static final String Businesses_POSTFIX = "businesses";

    public UpdatesReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Cursor cursor;

        cursor = context.getContentResolver().query(Uri.parse(PROVIDER_URI+ACTIVITIES_POSTFIX), null, null, null, null);

        if (cursor != null) {
            Activity activity = null;
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                try {
                    activity = CursorUtils.fromMatrixRow(activity, cursor);
                } catch (Exception e) {}

                if (activity != null)
                    RepositoriesFactory.getActivitiesRepository().addAndReturnAssignedId(activity);
            }
        }

        cursor = context.getContentResolver().query(Uri.parse(PROVIDER_URI+Businesses_POSTFIX), null, null, null, null);

        if (cursor != null) {
            Business business = null;
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                try {
                    business = CursorUtils.fromMatrixRow(business, cursor);
                } catch (Exception e) {}

                if (business != null)
                    RepositoriesFactory.getBusinessesRepository().addAndReturnAssignedId(business);
            }
        }
    }
}
