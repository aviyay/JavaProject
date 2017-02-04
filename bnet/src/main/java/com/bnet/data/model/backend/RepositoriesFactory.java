package com.bnet.data.model.backend;

import android.util.Log;

import com.bnet.data.model.datasource.PhpAccountsRepository;
import com.bnet.data.model.datasource.PhpActivityProvidableRepository;
import com.bnet.data.model.datasource.PhpBusinessProvidableRepository;
import com.bnet.shared.model.services.utils.ProvidableUtils;

public class RepositoriesFactory extends com.bnet.shared.model.backend.RepositoriesFactory {
    private static AccountsRepository accountsRepository = new PhpAccountsRepository();


    /*static {
        moveToCloud();
    }
*/

    /**
     * Move the repositories to the cloud repositories
     */
    public static void moveToCloud() {
        Log.d("MyCustomTag", "TNet: moveToCloud");

        if (!(accountsRepository instanceof PhpAccountsRepository))
            accountsRepository = new PhpAccountsRepository();
        if (!(activitiesRepository instanceof PhpActivityProvidableRepository))
            activitiesRepository = new PhpActivityProvidableRepository();
        if (!(businessesRepository instanceof PhpBusinessProvidableRepository))
            businessesRepository = new PhpBusinessProvidableRepository();

        ProvidableUtils.refreshRepositories();
    }

    /**
     * Get the accounts repository
     * @return The Account repository
     */
    public static AccountsRepository getAccountsRepository() {
        return accountsRepository;
    }
}
