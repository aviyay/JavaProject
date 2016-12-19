package com.bnet.data.model;

import android.app.IntentService;
import android.content.Intent;

import com.bnet.shared.model.backend.ProvidableRepository;
import com.bnet.data.model.backend.RepositoriesFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class Updater extends IntentService {
    private static List<ProvidableRepository> repositories = new ArrayList<>();
    private static final String UPDATE_ACTION = "com.bnet.action.UPDATE";

    public Updater() {
        super("Updater");

        repositories.add(RepositoriesFactory.getActivitiesRepository());
        repositories.add(RepositoriesFactory.getBusinessesRepository());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Timer timer = new Timer();
        TimerTask runCheck = new TimerTask() {
            @Override
            public void run() {
                runCheck();
            }
        };
        timer.scheduleAtFixedRate(runCheck, 0, 10000);
    }


    private void runCheck() {
        //Log.d("Updater", "Run checks");
        boolean shouldUpdate = false;

        for (int i = 0; i < repositories.size() && !shouldUpdate; i++)
            if (repositories.get(i).isSomethingNew())
                shouldUpdate = true;

        if (shouldUpdate)
            updateReceivers();
    }

    private void updateReceivers() {
        sendBroadcast(new Intent(UPDATE_ACTION));
    }
}


