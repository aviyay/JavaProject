package com.bnet.tnet.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bnet.shared.model.Constants;
import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.services.utils.ProvidableUtils;
import com.bnet.tnet.R;
import com.bnet.tnet.view.AgencyListRow;
import com.bnet.tnet.view.ShortTravelDetails;


public class TravelDetails extends android.app.Activity {
    private ShortTravelDetails shortTravelDetails;
    private AgencyListRow agencyListRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_details);

        findViews();

        registerAgencyListener();

        bindViews();
    }

    private void findViews() {
        shortTravelDetails = (ShortTravelDetails) findViewById(R.id.short_travel_details);
        agencyListRow = (AgencyListRow) findViewById(R.id.agency_list_row);
    }

    private void registerAgencyListener() {
        agencyListRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AgencyDetails.class, agencyListRow.getAgency());
            }
        });
    }

    private void bindViews() {
        Activity travel = retrieveTravelFromIntent(getIntent());
        Business agencyReference = getAgencyReference(travel.getBusinessId());

        shortTravelDetails.setTravel(travel);
        agencyListRow.setAgency(agencyReference);
    }

    private Activity retrieveTravelFromIntent(Intent intent) {
        Bundle bundle = intent.getBundleExtra(Constants.ACTIVITIES_URI_PATH);
        return ProvidableUtils.bundleConvert(Activity.class, bundle);
    }

    private Business getAgencyReference(long id) {
        return RepositoriesFactory
                .getBusinessesRepository()
                .getOrNull(id);
    }

    private void startActivity(Class activity, Providable providable) {
        Intent intent = new Intent(this, activity);

        Bundle bundle = ProvidableUtils.bundleConvert(providable);
        intent.putExtra(ProvidableUtils.getURIPath(providable), bundle);

        startActivity(intent);
    }
}
