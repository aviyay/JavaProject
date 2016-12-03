package com.students.aviyay_and_shy.bnet;

import com.students.aviyay_and_shy.bnet.model.backend.Database;
import com.students.aviyay_and_shy.bnet.model.datasource.ListsDatabase;
import com.students.aviyay_and_shy.bnet.model.entities.Account;
import com.students.aviyay_and_shy.bnet.model.entities.Activity;
import com.students.aviyay_and_shy.bnet.model.entities.Business;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ListsDatabaseTest {
    Database db;

    @Before
    public void setUp() throws Exception {
        db = new ListsDatabase();
    }

    @Test
    public void addBusinessAndGetAllBusinesses() throws Exception {
        Business business = new Business();

        db.addBusiness(business);

        List<Business> result = db.getAllBusinesses();

        Assert.assertTrue(result.contains(business));
    }

    @Test
    public void addActivityAndGetAllActivities() throws Exception {
        Activity activity = new Activity();

        db.addActivity(activity);

        List<Activity> result = db.getAllActivities();

        Assert.assertTrue(result.contains(activity));
    }

    @Test
    public void addAccountAndGetAllAccounts() throws Exception {
        Account account = new Account();

        db.addAccount(account);

        List<Account> result = db.getAllAccounts();

        Assert.assertTrue(result.contains(account));
    }

    @Test
    public void isBusinessChanged() throws Exception {
        Business business = new Business();

        Assert.assertFalse(db.isBusinessChanged());

        db.addBusiness(business);

        Assert.assertTrue(db.isBusinessChanged());
    }

    @Test
    public void isActivityChanged() throws Exception {
        Activity activity = new Activity();

        Assert.assertFalse(db.isActivityChanged());

        db.addActivity(activity);

        Assert.assertTrue(db.isActivityChanged());
    }

    @Test
    public void checkLastChanges() throws Exception {
        //TODO: what the devil we should check here?!
    }

}