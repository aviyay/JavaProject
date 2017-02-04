package com.bnet.shared.model.backend;

import android.util.Log;

import com.bnet.shared.model.datasource.ListProvidableRepository;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;

public class RepositoriesFactory {
    protected static ProvidableRepository<Activity> activitiesRepository = new ListProvidableRepository<>();
    protected static ProvidableRepository<Business> businessesRepository = new ListProvidableRepository<>();

    static {
        Log.d("MyCustomTag", "Shared: RepositoriesFactory, First Access");
    }

    /**
     * Get the activities repository
     * @return The activities repository
     */
    public static ProvidableRepository<Activity> getActivitiesRepository() {
        return activitiesRepository;
    }
    /**
     * Get the businesses repository
     * @return The businesses repository
     */
    public static ProvidableRepository<Business> getBusinessesRepository() {
        return businessesRepository;
    }

    /**
     * Set the businesses repository
     * @param businessesRepository the businesses repository to be set
     */
    public static void setBusinessesRepository(ProvidableRepository<Business> businessesRepository) {
        RepositoriesFactory.businessesRepository = businessesRepository;
    }
    /**
     * Set the activities repository
     * @param activitiesRepository the activities repository to be set
     */
    public static void setActivitiesRepository(ProvidableRepository<Activity> activitiesRepository) {
        RepositoriesFactory.activitiesRepository = activitiesRepository;
    }

}
