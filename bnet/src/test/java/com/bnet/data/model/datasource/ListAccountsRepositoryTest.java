package com.bnet.data.model.datasource;

import com.bnet.data.model.entities.Account;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ListAccountsRepositoryTest {
    private ListAccountsRepository repository;
    private Account account;
    @Before
    public void setUpAndAddAccount() throws Exception {
        repository = new ListAccountsRepository();

        account = new Account("Username", "Password");
        repository.add(account);
    }

    @Test
    public void validGet() throws Exception {
        Account result = repository.getOrNull(account.getUsername());
        assertEquals(account, result);
    }

    @Test
    public void nullGet() throws Exception {
        Account result = repository.getOrNull("INVALID USERNAME");
        assertNull(result);
    }

    @Test
    public void getAll() throws Exception {
        List<Account> accounts = repository.getAll();

        assertEquals(1, accounts.size());
        assertTrue(accounts.contains(account));
    }

}