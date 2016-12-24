package com.bnet.tnet.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.bnet.shared.model.Constants;
import com.bnet.shared.model.services.utils.CursorUtils;
import com.bnet.shared.model.services.utils.ProvidableUtils;
import com.bnet.shared.model.backend.Providable;

public class UpdatesReceiver extends BroadcastReceiver {
    public UpdatesReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Cursor cursor;

        for(Providable p : ProvidableUtils.getAllProvidable()) {
            String uri = "content://" + Constants.PROVIDER_AUTHORITY + "/" + ProvidableUtils.getURIPath(p);
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
                        ProvidableUtils.getRepository(p).addAndReturnAssignedId(temp);

                    cursor.moveToNext();
                }
            }
        }
    }
}
