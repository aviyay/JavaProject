package com.bnet.tnet.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bnet.shared.model.entities.Activity;
import com.bnet.tnet.R;

public class ShortTravelDetails extends LinearLayout {
    private TextView temp;

    private Activity travel;

    public ShortTravelDetails(Context context) {
        super(context);
        initializeView(context);
    }

    public ShortTravelDetails(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }

    public ShortTravelDetails(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context);
    }

    private void initializeView(Context context) {
        inflateViews(context);
        findViews();
    }

    private void inflateViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.short_travel_details, this);
    }

    private void findViews() {
        temp = (TextView) findViewById(R.id.travel_details_temp);
    }

    public Activity getTravel() {
        return travel;
    }

    public void setTravel(Activity travel) {
        this.travel = travel;
        bindViews();
    }

    private void bindViews() {
        temp.setText(travel.getDescription());
    }
}
