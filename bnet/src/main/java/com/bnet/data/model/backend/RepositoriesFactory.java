package com.bnet.data.model.backend;

import com.bnet.data.model.datasource.ListAccountsRepository;
import com.bnet.data.model.datasource.PhpAccountsRepository;
import com.bnet.data.model.datasource.PhpActivityProvideableRepository;
import com.bnet.data.model.datasource.PhpBusinessProvidableRepository;

public class RepositoriesFactory extends com.bnet.shared.model.backend.RepositoriesFactory{
    private static AccountsRepository accountsRepository = new ListAccountsRepository();


    static {
        moveToCloud();
    }

    public static AccountsRepository getAccountsRepository() {
        return accountsRepository;
    }

    public static void moveToCloud() {
        if (!(accountsRepository instanceof PhpAccountsRepository))
            accountsRepository = new PhpAccountsRepository();
        if (!(activitiesRepository instanceof PhpActivityProvideableRepository))
            activitiesRepository = new PhpActivityProvideableRepository();
        if (!(businessesRepository instanceof PhpBusinessProvidableRepository))
            businessesRepository = new PhpBusinessProvidableRepository();
    }
}
