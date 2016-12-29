package com.bnet.shared.model.backend;

import android.util.Log;

import com.bnet.shared.model.datasource.ListProvidableRepository;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;

public class RepositoriesFactory {
    private static final ProvidableRepository<Activity> activitiesRepository = new ListProvidableRepository<>();
    private static final ProvidableRepository<Business> businessesRepository = new ListProvidableRepository<>();

    static {
        Log.d("MyCustomTag", "Shared: RepositoriesFactory, First Access");
    }

    public static ProvidableRepository<Activity> getActivitiesRepository() {
        return activitiesRepository;
    }

    public static ProvidableRepository<Business> getBusinessesRepository() {
        return businessesRepository;
    }
}
