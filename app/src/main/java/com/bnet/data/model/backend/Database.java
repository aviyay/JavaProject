package com.bnet.data.model.backend;

import com.bnet.data.model.entities.Account;
import com.bnet.data.model.entities.Activity;
import com.bnet.data.model.entities.Business;

import java.util.List;

public interface Database {
    void addBusiness(Business business);
    void addActivity(Activity activity);
    void addAccount(Account account);

    boolean isBusinessChanged();
    boolean isActivityChanged();

    List<Account> getAllAccounts();
    List<Activity> getAllActivities();
    List<Business> getAllBusinesses();

    void checkLastChanges();
}
