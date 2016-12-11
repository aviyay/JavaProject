package com.bnet.data.model.backend;

import com.bnet.data.model.entities.Account;

import java.util.List;

public interface AccountsRepository {
    void add(Account account);
    Account getOrNull(String username);
    List<Account> getAll();
}
