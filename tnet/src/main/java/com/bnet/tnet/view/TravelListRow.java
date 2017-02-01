package com.bnet.tnet.view;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bnet.shared.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;
import com.bnet.tnet.R;

public class TravelListRow extends CardView {
    private TextView travelCountry;
    private TextView travelDates;
    private TextView travelAgency;
    private TextView travelPrice;

    private Activity travel;

    public TravelListRow(Context context) {
        super(context);
        initializeView(context);
    }

    public TravelListRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }

    public TravelListRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context);
    }

    private void initializeView(Context context) {
        setClickable(true);
        setRadius(4);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        inflateViews(context);
        findViews();
    }

    private void inflateViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.travel_list_row, this);
    }

    private void findViews() {
        travelCountry = (TextView) findViewById(R.id.travelCountry);
        travelDates = (TextView) findViewById(R.id.travelDates);
        travelAgency = (TextView) findViewById(R.id.travelAgency);
        travelPrice = (TextView) findViewById(R.id.travelPrice);
    }

    public Activity getTravel() {
        return travel;
    }

    public void setTravel(Activity travel) {
        this.travel = travel;
        bindViews();
    }

    private void bindViews() {
        travelCountry.setText(travel.getCountry());
        travelDates.setText(String.format("%s - %s", travel.getStart().toDateString(), travel.getEnd().toDateString()));
        travelPrice.setText(String.format("%.2f", travel.getPrice()));

        Business agencyReference = RepositoriesFactory
                .getBusinessesRepository()
                .getOrNull(travel.getBusinessId());

        if (agencyReference == null)
            throw new IllegalArgumentException("illegal business id");

        travelAgency.setText(agencyReference.getName());

    }

}
