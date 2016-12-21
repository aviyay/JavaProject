package com.bnet.tnet.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.bnet.shared.model.Constants;
import com.bnet.shared.model.CursorUtils;
import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;

import java.util.ArrayList;
import java.util.List;

public class UpdatesReceiver extends BroadcastReceiver {
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
            String uri = "content://" + Constants.PROVIDER_AUTHORITY + "/" + p.getURIPath();
            cursor = context.getContentResolver().query(Uri.parse(uri), null, null, null, null);

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
