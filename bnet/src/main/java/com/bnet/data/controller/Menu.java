package com.bnet.data.controller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.bnet.data.R;

public class Menu extends Activity {

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initializeAddBusinessButton();
        initializeAddActivityButton();
        initializeSignOutButton();
    }

    /**
     * Initialize Add Business Button
     */
    private void initializeAddBusinessButton() {
        findViewById(R.id.addBusinessBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(getApplicationContext(),BusinessEditor.class);
                startActivity(k);
            }
        });
    }

    /**
     * Initialize Add Activity Button
     */
    private void initializeAddActivityButton() {
        findViewById(R.id.addActivityBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(getApplicationContext() ,ActivityEditor.class);
                startActivity(k);
            }
        });
    }

    /**
     * Initialize Sign Out Button
     */
    private void initializeSignOutButton() {
        findViewById(R.id.signOutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor =  sharedPreferences.edit();
                editor.remove("userLogIn");
                editor.apply();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.click_back_again_to_logout, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
