package com.bnet.data.model.backend;

import com.bnet.data.model.entities.Account;

import java.util.List;

public interface AccountsRepository {
    /**
     * Add account to the repository
     * @param account The account to be added
     */
    void add(Account account);

    /**
     * Get the account
     * @param username The account username
     * @return The account if there is an account with that username
     * if there isn't then NULL gets returned
     */
    Account getOrNull(String username);

    /**
     * Get all accounts
     * @return List of all of the accounts
     */
    List<Account> getAll();
}
