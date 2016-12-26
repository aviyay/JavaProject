package com.bnet.tnet.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.bnet.shared.model.Constants;
import com.bnet.shared.model.services.utils.CursorUtils;
import com.bnet.shared.model.services.utils.ProvidableUtils;
import com.bnet.shared.model.backend.Providable;

public class UpdatesReceiver extends BroadcastReceiver {
    public UpdatesReceiver() {
        Log.d("MyCustomTag", "TNet: UpdatesReceiverCreated");
    }

    //TODO: split into first query and period news query, and take only the TRAVEL activities and their businesses

    @Override
    public void onReceive(Context context, Intent intent) {
        Cursor cursor;
        Log.d("MyCustomTag", "TNet: BroadCast received");

        for(Providable p : ProvidableUtils.getAllProvidable()) {
            ProvidableUtils.getRepository(p).reset();
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
                    Log.d("MyCustomTag", "TNet: Added "+temp.toString());

                    if (temp != null)
                        ProvidableUtils.getRepository(p).addAndReturnAssignedId(temp);

                    cursor.moveToNext();
                }
            }
        }
    }
}
