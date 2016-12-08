package com.bnet.data.model.datasource;

import com.bnet.data.model.backend.Database;
import com.bnet.data.model.entities.Account;
import com.bnet.data.model.entities.Activity;
import com.bnet.data.model.entities.Business;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ListsDatabaseTest {
    private Database db = new ListsDatabase();

    @Test
    public void addBusinessAndGetAllBusinesses() throws Exception {
        Business business = new Business();

        db.addBusiness(business);

        List<Business> result = db.getAllBusinesses();

        assertTrue(result.contains(business));
    }

    @Test
    public void addActivityAndGetAllActivities() throws Exception {
        Activity activity = new Activity();

        db.addActivity(activity);

        List<Activity> result = db.getAllActivities();

        assertTrue(result.contains(activity));
    }

    @Test
    public void addAccountAndGetAllAccounts() throws Exception {
        Account account = new Account();

        db.addAccount(account);

        List<Account> result = db.getAllAccounts();

        assertTrue(result.contains(account));
    }

    @Test
    public void isBusinessChanged() throws Exception {
        Business business = new Business();

        assertFalse(db.isBusinessChanged());

        db.addBusiness(business);

        assertTrue(db.isBusinessChanged());
    }

    @Test
    public void isActivityChanged() throws Exception {
        Activity activity = new Activity();

        assertFalse(db.isActivityChanged());

        db.addActivity(activity);

        assertTrue(db.isActivityChanged());
    }

    @Test
    public void checkLastChanges() throws Exception {
        //TODO: what the devil we should check here?!
    }
}