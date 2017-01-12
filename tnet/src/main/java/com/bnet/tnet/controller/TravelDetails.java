package com.bnet.tnet.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bnet.shared.model.Constants;
import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.services.utils.ProvidableUtils;
import com.bnet.tnet.R;
import com.bnet.tnet.Router;
import com.bnet.tnet.view.AgencyListRow;
import com.bnet.tnet.view.ShortTravelDetails;


public class TravelDetails extends android.app.Activity {
    private ShortTravelDetails shortTravelDetails;
    private AgencyListRow agencyListRow;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_details);

        findViews();

        registerAgencyListener();
        setUpToolBar();

        bindViews();
    }
    private void setUpToolBar() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void findViews() {
        shortTravelDetails = (ShortTravelDetails) findViewById(R.id.short_travel_details);
        agencyListRow = (AgencyListRow) findViewById(R.id.agency_list_row);
        toolbar = (Toolbar) findViewById(R.id.toolbar_back);
    }

    private void registerAgencyListener() {
        agencyListRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.getInstance().startActivity(v.getContext(), AgencyDetails.class, agencyListRow.getAgency());
            }
        });
    }

    private void bindViews() {
        Activity travel = retrieveTravelFromIntent(getIntent());
        shortTravelDetails.setTravel(travel);
        toolbar.setTitle(travel.getCountry());
        new AsyncTask<Long,Void,Business>()
        {
            @Override
            protected Business doInBackground(Long... params) {

               return getAgencyReference(params[0]);
            }

            @Override
            protected void onPostExecute(Business agencyReference) {
                super.onPostExecute(agencyReference);
                agencyListRow.setAgency(agencyReference);

            }
        }.execute(travel.getBusinessId());

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
}
