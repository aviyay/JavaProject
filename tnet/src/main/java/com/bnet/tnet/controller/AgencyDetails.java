package com.bnet.tnet.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bnet.shared.model.Constants;
import com.bnet.shared.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.services.utils.ProvidableUtils;
import com.bnet.shared.model.entities.Business;
import com.bnet.tnet.R;
import com.bnet.tnet.model.FilterDecorator;
import com.bnet.tnet.model.SearchFilter;

public class AgencyDetails extends android.app.Activity {
    private Business agency = new Business();

    private TextView agencyTemp;
    private TextView travelTemp;

    private RecyclerView recyclerView;
    private BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agency_details);

        retrieveBusinessFromIntent(getIntent());

        findViews();

        setupBottomSheetBehavior();
        setupTravelsAdapter();

        bindAgencyDetails();
    }

    private void retrieveBusinessFromIntent(Intent intent) {
        Bundle bundle = intent.getBundleExtra(Constants.BUSINESSES_URI_PATH);

        agency = (Business) ProvidableUtils.bundleConvert(Business.class, bundle);
    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        agencyTemp = (TextView) findViewById(R.id.agency_details_temp);
        travelTemp = (TextView) findViewById(R.id.travel_details_temp);
    }


    private void setupBottomSheetBehavior() {
        View bottomSheet = findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    private void setupTravelsAdapter() {
        SearchFilter<Activity> myTravelsFilter = new SearchFilter<Activity>() {
            @Override
            public boolean search(Activity item) {
                return item.getBusinessId() == agency.getId();
            }
        };

        FilterDecorator myTravels = new FilterDecorator(RepositoriesFactory.getActivitiesRepository(), myTravelsFilter);
        TravelsAdapter adapter = new TravelsAdapter(myTravels);

        adapter.setOnItemClickListener(new TravelsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Activity travel) {
                bindTravelDetails(travel);
                showBottomSheet();
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private void bindTravelDetails(Activity travel) {
        travelTemp.setText(travel.getDescription());
    }

    private void showBottomSheet() {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void bindAgencyDetails() {
        agencyTemp.setText(agency.getName());
    }
}
