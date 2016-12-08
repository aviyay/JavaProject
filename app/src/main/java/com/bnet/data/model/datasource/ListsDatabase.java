package com.bnet.data.model.datasource;

import com.bnet.data.model.backend.Database;
import com.bnet.data.model.entities.Account;
import com.bnet.data.model.entities.Activity;
import com.bnet.data.model.entities.Business;

import java.util.ArrayList;
import java.util.List;

public class ListsDatabase implements Database {

    private class ListRepository<T> {
        private List<T> items = new ArrayList<>();

        private boolean isChanged = false;

        boolean isChanged() {

            if (isChanged) {
                isChanged = false;
                return true;
            }

            return false;
        }

        void add(T item) {
            items.add(item);
            isChanged = true;
        }
        void remove(T item) {
            items.remove(item);
            isChanged = true;
        }

        List<T> getAll() {
            return items;
        }
    }

    private ListRepository<Business> businesses = new ListRepository<>();
    private ListRepository<Activity> activities = new ListRepository<>();
    private ListRepository<Account> accounts = new ListRepository<>();

    @Override
    public void addBusiness(Business business) {
        businesses.add(business);
    }

    @Override
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    @Override
    public void addAccount(Account account) {
        accounts.add(account);
    }

    @Override
    public boolean isBusinessChanged() {
        return businesses.isChanged();
    }

    @Override
    public boolean isActivityChanged() {
        return activities.isChanged();
    }

    @Override
    public List<Account> getAllAccounts() {
        return accounts.getAll();
    }

    @Override
    public List<Activity> getAllActivities() {
        return activities.getAll();
    }

    @Override
    public List<Business> getAllBusinesses() {
        return businesses.getAll();
    }

    @Override
    public void checkLastChanges() {
        //TODO: Understand what we should do here
    }
}
