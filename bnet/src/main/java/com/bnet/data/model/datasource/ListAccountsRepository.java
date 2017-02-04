package com.bnet.data.model.datasource;

import com.bnet.data.model.backend.AccountsRepository;
import com.bnet.data.model.entities.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ListAccountsRepository implements AccountsRepository {
    private Map<String, Account> accounts = new HashMap<>();

    @Override
    public void add(Account account) {
        accounts.put(account.getUsername(), account);
    }

    @Override
    public Account getOrNull(String username) {
        return accounts.get(username);
    }

    @Override
    public List<Account> getAll() {
        return new ArrayList<>(accounts.values());
    }
}
