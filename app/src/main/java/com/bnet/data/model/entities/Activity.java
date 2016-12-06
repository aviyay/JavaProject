package com.bnet.data.model.entities;

public class Activity {
    private ActivityType activityType;
    private String country;
    private DateTime start;
    private DateTime end;
    private double price;
    private String description;
    private int businessId;

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
