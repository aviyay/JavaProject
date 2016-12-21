package com.bnet.tnet.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.bnet.shared.model.CursorUtils;
import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;

import java.util.ArrayList;
import java.util.List;

public class UpdatesReceiver extends BroadcastReceiver {
    public static final String PROVIDER_URI = "com.bnet.provider";
    public static final String ACTIVITIES_POSTFIX = "/activities";
    public static final String Businesses_POSTFIX = "/businesses";
    private static List<Providable> providableList = null;

    static {
        providableList = new ArrayList<>();
        providableList.add(new Activity());
        providableList.add(new Business());
    }

    public UpdatesReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Cursor cursor;

        for(Providable p : providableList) {
            cursor = context.getContentResolver().query(Uri.parse("content://" + PROVIDER_URI + "/" + p.getURIPath()), null, null, null, null);
            if (cursor != null) {
                Providable temp = null;

                cursor.moveToFirst();

                while (!cursor.isAfterLast()) {
                    try {
                        temp = CursorUtils.fromMatrixRow(p, cursor);
                    } catch (Exception e) {
                    }

                    if (temp != null)
                        p.getRepository().addAndReturnAssignedId(temp);

                    cursor.moveToNext();
                }
            }
        }
    }
}
