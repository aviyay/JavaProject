package com.bnet.tnet.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bnet.shared.model.Constants;
import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.entities.EntitiesSamples;
import com.bnet.shared.model.services.utils.ProvidableUtils;
import com.bnet.tnet.R;


public class TravelDetails extends Activity {
    private com.bnet.shared.model.entities.Activity travel = new com.bnet.shared.model.entities.Activity();
    private Business agencyReference;

    private TextView temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_details);

        retrieveTravelFromIntent(getIntent());
        findAgencyReference();

        findViews();

        bindTravelDetails();
        bindAgencyReference();
    }

    private void retrieveTravelFromIntent(Intent intent) {
        Bundle bundle = intent.getBundleExtra(Constants.ACTIVITIES_URI_PATH);

        travel = (com.bnet.shared.model.entities.Activity) ProvidableUtils.bundleConvert(travel.getClass(), bundle);
    }

    private void findAgencyReference() {
        agencyReference = EntitiesSamples.getBusiness2();
    }

    private void findViews() {
        temp = (TextView) findViewById(R.id.travel_details_temp);
    }

    private void bindTravelDetails() {
        temp.setText(travel.getDescription());
    }

    private void bindAgencyReference() {
        View agencyListRow = findViewById(R.id.agency_list_row);
        agencyListRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AgencyDetails.class, agencyReference);
            }
        });
    }

    private void startActivity(Class activity, Providable providable) {
        Intent intent = new Intent(this, activity);

        Bundle bundle = ProvidableUtils.bundleConvert(providable);
        intent.putExtra(ProvidableUtils.getURIPath(providable), bundle);

        startActivity(intent);
    }
}
