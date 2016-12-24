package com.bnet.tnet.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.bnet.shared.model.Constants;
import com.bnet.shared.model.services.utils.ProvidableUtils;
import com.bnet.shared.model.entities.Business;
import com.bnet.tnet.R;

public class AgencyDetails extends Activity {
    Business agency = new Business();

    TextView temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agency_details);

        temp = (TextView) findViewById(R.id.agency_details_temp);

        retrieveBusinessFromIntent(getIntent());

        temp.setText(agency.getName());
    }

    private void retrieveBusinessFromIntent(Intent intent) {
        Bundle bundle = intent.getBundleExtra(Constants.BUSINESSES_URI_PATH);

        agency = (Business) ProvidableUtils.bundleConvert(Business.class, bundle);
    }
}
