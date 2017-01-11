package com.bnet.tnet.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bnet.shared.model.entities.Activity;
import com.bnet.tnet.R;

public class ShortTravelDetails extends LinearLayout {
    private TextView travelCountry;
    private TextView travelPrice;
    private TextView travelStartDate;
    private TextView travelStartTime;
    private TextView travelEndDate;
    private TextView travelEndTime;
    private TextView travelDescription;

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
        travelCountry=(TextView)findViewById(R.id.travelCountry);
        travelPrice=(TextView)findViewById(R.id.travelPrice);
        travelStartDate=(TextView)findViewById(R.id.travelStartDate);
        travelStartTime=(TextView)findViewById(R.id.travelStartTime);
        travelEndDate=(TextView)findViewById(R.id.travelEndDate);
        travelEndTime=(TextView)findViewById(R.id.travelEndTime);
        travelDescription=(TextView)findViewById(R.id.travelDescription);
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
        travelPrice.setText(String.format("%.2f", travel.getPrice()));
        travelStartDate.setText(travel.getStart().toDateString());
        travelStartTime.setText(travel.getStart().toHourString());
        travelEndDate.setText(travel.getEnd().toDateString());
        travelEndTime.setText(travel.getEnd().toHourString());
        travelDescription.setText(travel.getDescription());

    }
}
