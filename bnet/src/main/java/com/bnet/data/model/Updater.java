package com.bnet.data.model;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.bnet.shared.model.Constants;
import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.backend.ProvidableRepository;
import com.bnet.shared.model.services.utils.ProvidableUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Updater extends IntentService {
    private static List<ProvidableRepository> repositories = new ArrayList<>();
    private static final int INTERVAL = 10000;

    /**
     * Adds the repositories to a local list to be check later when the service starts
     */
    public Updater() {
        super("Updater");

        if (repositories.size() == 0)
            for (Class<? extends Providable> p : ProvidableUtils.getAllProvidable())
                repositories.add(ProvidableUtils.getRepository(p));
    }

    /**
     * Start the Schedule Task for the Checks
     * @param intent The intent used to open the service
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        Timer timer = new Timer();
        TimerTask runCheck = new TimerTask() {
            @Override
            public void run() {
                runCheck();
            }
        };
        timer.scheduleAtFixedRate(runCheck, 0, INTERVAL);
    }

    /**
     * Perform Checks whether there is something new in the database, if there is calls updateReceivers
     */
    private void runCheck() {
        Log.d("MyCustomTag", "BNet: Run checks");

        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected Void doInBackground(Void... params) {
                boolean shouldUpdate = false;

                for (int i = 0; i < repositories.size() && !shouldUpdate; i++)
                    if (repositories.get(i).isSomethingNew())
                        shouldUpdate = true;

                if (shouldUpdate)
                    updateReceivers();
                return null;
            }
        }.execute();

    }

    /**
     * Send out a Broadcast message that there is an update in the content provider
     */
    private void updateReceivers() {
        Log.d("MyCustomTag", "BNet: Sending broadcast");
        sendBroadcast(new Intent(Constants.UPDATE_ACTION));
    }
}


