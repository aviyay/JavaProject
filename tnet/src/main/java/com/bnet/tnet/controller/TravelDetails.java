package com.bnet.tnet.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.bnet.shared.model.Constants;
import com.bnet.shared.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.services.utils.ProvidableUtils;
import com.bnet.tnet.R;
import com.bnet.tnet.Router;
import com.bnet.tnet.view.AgencyListRow;
import com.bnet.tnet.view.ShortTravelDetails;

/**
 * An activity to show a travel details
 */
public class TravelDetails extends android.app.Activity {

    private ShortTravelDetails shortTravelDetails;
    private AgencyListRow agencyListRow;
    private Toolbar toolbar;
    private ImageButton shareBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_details);

        findViews();

        registerAgencyListener();
        setUpToolBar();

        bindViews();
    }
    /**
     * Setup the return button
     */
    private void setUpToolBar() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    /**
     * Find all the needed xml views
     */
    private void findViews() {
        shortTravelDetails = (ShortTravelDetails) findViewById(R.id.short_travel_details);
        agencyListRow = (AgencyListRow) findViewById(R.id.agency_list_row);
        toolbar = (Toolbar) findViewById(R.id.toolbar_back);
        shareBtn=(ImageButton)findViewById(R.id.shareBtn);
    }

    /**
     * Setup the associated agency in the AgencyListRow
     */
    private void registerAgencyListener() {
        agencyListRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.getInstance().startActivity(v.getContext(), AgencyDetails.class, agencyListRow.getAgency());
            }
        });
    }

    /**
     * Bind the details and actions to their appropriate views
     */
    private void bindViews() {
        final Activity travel = retrieveTravelFromIntent(getIntent());
        shortTravelDetails.setTravel(travel);
        toolbar.setTitle(travel.getCountry());
        agencyListRow.setAgency(getAgencyReference(travel.getBusinessId()));
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, String.format(getString(R.string.share_msg_trip), travel.getStart().toDateString(), travel.getEnd().toDateString(), travel.getDescription(), travel.getPrice()));
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent,getString(R.string.share_trip_to)));
            }
        });
    }

    /**
     * Retrieve the travel from an intent
     * @param intent The intent that contains the travel
     * @return The travel that was contained in the intent
     */
    private Activity retrieveTravelFromIntent(Intent intent) {
        Bundle bundle = intent.getBundleExtra(Constants.ACTIVITIES_URI_PATH);
        return ProvidableUtils.bundleConvert(Activity.class, bundle);
    }

    /**
     * Get the associated agency with this travel
     * @param id the Id of the agency
     * @return The associated agency
     */
    private Business getAgencyReference(long id) {
        return RepositoriesFactory
                .getBusinessesRepository()
                .getOrNull(id);
    }
}
