package com.bnet.tnet.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bnet.shared.model.Constants;
import com.bnet.shared.model.services.utils.ProvidableUtils;
import com.bnet.shared.model.entities.Business;
import com.bnet.tnet.R;

public class AgencyDetails extends Activity {
    private Business agency = new Business();

    private TextView temp;
    private BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agency_details);

        temp = (TextView) findViewById(R.id.agency_details_temp);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        View bottomSheet = findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        retrieveBusinessFromIntent(getIntent());
        temp.setText(agency.getName());
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    private void retrieveBusinessFromIntent(Intent intent) {
        Bundle bundle = intent.getBundleExtra(Constants.BUSINESSES_URI_PATH);

        agency = (Business) ProvidableUtils.bundleConvert(Business.class, bundle);
    }
}
