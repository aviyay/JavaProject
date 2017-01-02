package com.bnet.tnet.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bnet.shared.model.Constants;
import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.services.utils.ProvidableUtils;
import com.bnet.tnet.R;
import com.bnet.tnet.view.ShortTravelDetails;


public class TravelDetails extends android.app.Activity {
    private Activity travel = new Activity();
    private Business agencyReference;

    private ShortTravelDetails shortTravelDetails;
    private TextView agencyName;
    private TextView agencyStreet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_details);

        retrieveTravelFromIntent(getIntent());
        agencyReference = findAgencyReference(travel.getBusinessId());

        findViews();

        bindShortDetails();
        bindAgencyReference();
    }

    private void retrieveTravelFromIntent(Intent intent) {
        Bundle bundle = intent.getBundleExtra(Constants.ACTIVITIES_URI_PATH);

        travel = ProvidableUtils.bundleConvert(travel.getClass(), bundle);
    }

    private Business findAgencyReference(int businessId) {

        for (Business business : RepositoriesFactory.getBusinessesRepository().getAll())
            if (business.getId() == businessId) {
                return business;
            }

        return null;
    }

    private void findViews() {
        agencyName = (TextView) findViewById(R.id.agencyName);
        agencyStreet = (TextView) findViewById(R.id.agencyStreet);

        shortTravelDetails = (ShortTravelDetails) findViewById(R.id.short_travel_details);
    }

    private void bindShortDetails() {
        shortTravelDetails.setTravel(travel);
    }

    void bindAgencyReference() {
        agencyName.setText(agencyReference.getName());
        agencyStreet.setText(agencyReference.getAddress().getCountry());

        registerAgencyListener();
    }

    private void registerAgencyListener() {

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
