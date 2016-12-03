package com.students.aviyay_and_shy.bnet.model.backend;

import com.students.aviyay_and_shy.bnet.model.entities.Account;
import com.students.aviyay_and_shy.bnet.model.entities.Activity;
import com.students.aviyay_and_shy.bnet.model.entities.Business;

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
