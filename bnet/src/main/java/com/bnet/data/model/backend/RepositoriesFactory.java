package com.bnet.data.model.backend;

import com.bnet.data.model.datasource.ListAccountsRepository;

public class RepositoriesFactory extends com.bnet.shared.model.backend.RepositoriesFactory{
    private static AccountsRepository accountsRepository = new ListAccountsRepository();

    static {
        //moveToCloud();
    }
/*
    private static void moveToCloud() {
        accountsRepository = new PhpAccountsRepository();
        activitiesRepository = new PhpActivityProvideableRepository();
        businessesRepository = new PhpBusinessProvidableRepository();
    }
*/
    public static AccountsRepository getAccountsRepository() {
        return accountsRepository;
    }
}
