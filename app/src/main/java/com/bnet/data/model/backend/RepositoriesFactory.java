package com.bnet.data.model.backend;

import com.bnet.data.model.datasource.ListAccountsRepository;
import com.bnet.data.model.datasource.PhpAccountsRepository;

public class RepositoriesFactory extends com.bnet.shared.model.backend.RepositoriesFactory{
    private static AccountsRepository accountsRepository = new ListAccountsRepository();

    public static AccountsRepository getAccountsRepository() {
        return accountsRepository;
    }
}
