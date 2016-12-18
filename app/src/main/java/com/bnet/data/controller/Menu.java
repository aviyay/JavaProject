package com.bnet.data.controller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.bnet.data.R;

public class Menu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initializeAddBusinessButton();
        initializeAddActivityButton();
        initializeSignOutButton();
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
                editor.remove("isLogIn");
                finish();
            }
        });
    }
}
