package com.bnet.tnet.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bnet.shared.model.entities.Business;
import com.bnet.tnet.R;

public class AgencyListRow extends CardView {
    private TextView agencyName;
    private TextView agencyStreet;

    private Business agency;

    public AgencyListRow(Context context) {
        super(context);
        initializeView(context);
    }

    public AgencyListRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }

    public AgencyListRow(Context context, AttributeSet attrs, int defStyleAttr) {
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

        inflater.inflate(R.layout.agency_list_row, this);
    }

    private void findViews() {
        agencyName = (TextView) findViewById(R.id.agencyName);
        agencyStreet = (TextView) findViewById(R.id.agencyStreet);
    }

    public Business getAgency() {
        return agency;
    }

    public void setAgency(Business agency) {
        this.agency = agency;
        bindViews();
    }

    private void bindViews() {
        agencyName.setText(agency.getName());
        agencyStreet.setText(agency.getAddress().getCountry());
    }

}
