package com.bnet.tnet.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.bnet.shared.model.Constants;
import com.bnet.shared.model.services.utils.ProvidableUtils;
import com.bnet.tnet.R;

public class TravelDetails extends Activity {
    com.bnet.shared.model.entities.Activity travel = new com.bnet.shared.model.entities.Activity();

    TextView temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_details);

        temp = (TextView) findViewById(R.id.travel_details_temp);

        retrieveActivityFromIntent(getIntent());

        temp.setText(travel.getDescription());
    }

    private void retrieveActivityFromIntent(Intent intent) {
        Bundle bundle = intent.getBundleExtra(Constants.ACTIVITIES_URI_PATH);

        travel = (com.bnet.shared.model.entities.Activity) ProvidableUtils.bundleConvert(travel.getClass(), bundle);
    }
}
