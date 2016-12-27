package com.bnet.shared.model.backend;

import com.bnet.shared.model.datasource.ListProvidableRepository;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;

public class RepositoriesFactory {
    protected static ProvidableRepository<Activity> activitiesRepository = new ListProvidableRepository<>();
    protected static ProvidableRepository<Business> businessesRepository = new ListProvidableRepository<>();

    public static ProvidableRepository<Activity> getActivitiesRepository() {
        return activitiesRepository;
    }

    public static ProvidableRepository<Business> getBusinessesRepository() {
        return businessesRepository;
    }
}
