package com.bnet.data.controller;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import com.bnet.data.R;

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
