package com.bnet.data.model.entities;

import android.content.ContentValues;

import com.bnet.data.model.ContentValuesConverter;
import com.bnet.data.model.backend.Providable;
import com.bnet.data.model.backend.ProvidableRepository;
import com.bnet.data.model.backend.RepositoriesFactory;

public class Activity implements Providable<Activity> {
    private int id = -1;
    private ActivityType activityType;
    private String country;
    private DateTime start;
    private DateTime end;
    private double price;
    private String description;
    private int businessId;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getURIPath() {
        return "activities";
    }

    @Override
    public ProvidableRepository<Activity> getRepository() {
        return RepositoriesFactory.getActivitiesRepository();
    }

    @Override
    public Activity fromContentValues(ContentValues contentValues) {
        try {
            return ContentValuesConverter.contentValuesToActivity(contentValues);
        } catch (Exception e) {
            throw new IllegalArgumentException("This contentValues is not a valid activity");
        }
    }


    public ActivityType getType() {
        return activityType;
    }

    public void setType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public DateTime getStart() {
        return start;
    }

    public void setStart(DateTime start) {
        this.start = start;
    }

    public DateTime getEnd() {
        return end;
    }

    public void setEnd(DateTime end) {
        this.end = end;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }
}
