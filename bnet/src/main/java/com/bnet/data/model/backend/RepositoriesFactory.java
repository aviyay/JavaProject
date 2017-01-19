package com.bnet.data.model.backend;

import android.util.Log;

import com.bnet.data.model.datasource.ListAccountsRepository;
import com.bnet.data.model.datasource.PhpAccountsRepository;
import com.bnet.data.model.datasource.PhpActivityProvideableRepository;
import com.bnet.data.model.datasource.PhpBusinessProvidableRepository;
import com.bnet.shared.model.services.utils.ProvidableUtils;

public class RepositoriesFactory extends com.bnet.shared.model.backend.RepositoriesFactory {
    private static AccountsRepository accountsRepository = new PhpAccountsRepository();


    /*static {
        moveToCloud();
    }
*/
    public static void moveToCloud() {
        Log.d("MyCustomTag", "TNet: moveToCloud");

        if (!(accountsRepository instanceof PhpAccountsRepository))
            accountsRepository = new PhpAccountsRepository();
        if (!(activitiesRepository instanceof PhpActivityProvideableRepository))
            activitiesRepository = new PhpActivityProvideableRepository();
        if (!(businessesRepository instanceof PhpBusinessProvidableRepository))
            businessesRepository = new PhpBusinessProvidableRepository();

        ProvidableUtils.refreshRepositories();
    }

    public static AccountsRepository getAccountsRepository() {
        return accountsRepository;
    }
}
