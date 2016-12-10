package com.bnet.data.controller;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.bnet.data.R;
import com.bnet.data.model.backend.DatabaseFactory;
import com.bnet.data.model.entities.Business;

import java.util.List;

public class ViewBusiness extends Activity {
    ListView businessList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_business);
       // getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
