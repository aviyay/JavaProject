package com.bnet.data.model.backend;

import com.bnet.data.model.datasource.ListProvidableRepository;
import com.bnet.data.model.entities.Activity;
import com.bnet.data.model.entities.Business;

public class RepositoriesFactory {
    private static AccountsRepository accountsRepository;

    private static ProvidableRepository<Activity> activitiesRepository = new ListProvidableRepository<>();
    private static ProvidableRepository<Business> businessesRepository = new ListProvidableRepository<>();

    public static AccountsRepository getAccountsRepository() {
        return accountsRepository;
    }

    public static ProvidableRepository<Activity> getActivitiesRepository() {
        return activitiesRepository;
    }

    public static ProvidableRepository<Business> getBusinessesRepository() {
        return businessesRepository;
    }
}
