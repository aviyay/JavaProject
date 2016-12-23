package com.bnet.tnet.controller;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.bnet.tnet.R;

import org.w3c.dom.Text;

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
        Bundle bundle = intent.getExtras();

        ContentValues values = new ContentValues();

        for (String key : bundle.keySet()) {
            values.put(key, bundle.getString(key));
        }

        travel = travel.fromContentValues(values);
    }
}
