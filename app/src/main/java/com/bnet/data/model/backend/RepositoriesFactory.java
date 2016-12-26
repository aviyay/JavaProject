package com.bnet.data.model.backend;

import com.bnet.data.model.datasource.ListAccountsRepository;

public class RepositoriesFactory extends com.bnet.shared.model.backend.RepositoriesFactory{
    private static AccountsRepository accountsRepository = new ListAccountsRepository();

    public static AccountsRepository getAccountsRepository() {
        return accountsRepository;
    }
}
