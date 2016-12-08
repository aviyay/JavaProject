package com.bnet.data.model.datasource;

import android.os.AsyncTask;

import com.bnet.data.model.backend.Database;
import com.bnet.data.model.entities.Account;
import com.bnet.data.model.entities.Activity;
import com.bnet.data.model.entities.Business;

import java.util.List;

/**
 * Created by shyte on 8/12/2016.
 */

public class DatabaseAsyncDecorator implements Database {
    Database db;
    DatabaseAsyncDecorator(Database db)
    {
        this.db=db;
    }


    @Override
    public void addBusiness(Business business) {
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public void addActivity(Activity activity) {
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public void addAccount(Account account) {
        class AddAccountAsync extends AsyncTask<Account,Void,Void> {
            @Override
            protected Void doInBackground(Account... params) {
               db.addAccount(params[0]);
                return null;
            }
        }
        new AddAccountAsync().execute(account);
    }

    @Override
    public boolean isBusinessChanged() {
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public boolean isActivityChanged() {
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public List<Account> getAllAccounts() {
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public List<Activity> getAllActivities() {
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public List<Business> getAllBusinesses() {
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public void checkLastChanges() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
