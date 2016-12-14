package com.bnet.data.controller;

import android.os.Bundle;
import android.app.Activity;

import com.bnet.data.R;

public class ActivityEditor extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_editor);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
