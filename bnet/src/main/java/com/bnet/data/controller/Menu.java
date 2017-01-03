package com.bnet.data.controller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.bnet.data.R;
import com.bnet.shared.model.backend.RepositoriesFactory;

import java.util.List;

public class Menu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initializeAddBusinessButton();
        initializeAddActivityButton();
        initializeSignOutButton();
       /* new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected Void doInBackground(Void... params) {
                boolean result=RepositoriesFactory.getActivitiesRepository().isSomethingNew();
                RepositoriesFactory.getActivitiesRepository().getAllNews();
                boolean r=RepositoriesFactory.getActivitiesRepository().isSomethingNew();

                boolean result2=RepositoriesFactory.getBusinessesRepository().isSomethingNew();
                RepositoriesFactory.getBusinessesRepository().getAllNews();
                boolean r2=RepositoriesFactory.getBusinessesRepository().isSomethingNew();
                return null;
            }
        }.execute();*/


    }

    private void initializeAddBusinessButton() {
        findViewById(R.id.addBusinessBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(getApplicationContext(),BusinessEditor.class);
                startActivity(k);
            }
        });
    }
    private void initializeAddActivityButton() {
        findViewById(R.id.addActivityBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(getApplicationContext() ,ActivityEditor.class);
                startActivity(k);
            }
        });
    }
    private void initializeSignOutButton() {
        findViewById(R.id.signOutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor =  sharedPreferences.edit();
                editor.remove("userLogIn");
                editor.commit();
                finish();
            }
        });
    }
}
