package com.bnet.shared.model.entities;

import com.bnet.shared.model.backend.Providable;

public class Activity implements Providable {
    private long id = -1;
    private ActivityType activityType = ActivityType.TRAVEL;
    private String country = "";
    private DateTime start = new DateTime();
    private DateTime end = new DateTime();
    private double price;
    private String description = "";
    /**
     * The Business ID of the Business that provide this activity
     */
    private long businessId;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get Type
     * @return The type of the activity
     */
    public ActivityType getType() {
        return activityType;
    }
    /**
     * Set Type
     * @param activityType The type to be set
     */
    public void setType(ActivityType activityType) {
        this.activityType = activityType;
    }
    /**
     * Get Country
     * @return The Country of the activity
     */
    public String getCountry() {
        return country;
    }
    /**
     * Set Country
     * @param country The country to be set
     */
    public void setCountry(String country) {
        this.country = country;
    }
    /**
     * Get Start Time
     * @return The Start Time of the activity
     */
    public DateTime getStart() {
        return start;
    }
    /**
     * Set Start Date
     * @param start The Start Date to be set
     */
    public void setStart(DateTime start) {
        this.start = start;
    }
    /**
     * Get End Time
     * @return The End Time of the activity
     */
    public DateTime getEnd() {
        return end;
    }
    /**
     * Set End Date
     * @param end The End Date to be set
     */
    public void setEnd(DateTime end) {
        this.end = end;
    }
    /**
     * Get Price
     * @return The Price of the activity
     */
    public double getPrice() {
        return price;
    }
    /**
     * Set Price
     * @param price The price to be set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * Get Description
     * @return The Description of the activity
     */
    public String getDescription() {
        return description;
    }
    /**
     * Set Description
     * @param description The description to be set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Get Business Id of the business that provide this activity
     * @return The Business Id of of the activity
     */
    public long getBusinessId() {
        return businessId;
    }
    /**
     * Set Business ID
     * @param businessId The Business ID to be set
     */
    public void setBusinessId(long businessId) {
        this.businessId = businessId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;

        return Double.compare(activity.getPrice(), getPrice()) == 0
                && getBusinessId() == activity.getBusinessId()
                && activityType == activity.activityType
                && getCountry().equals(activity.getCountry())
                && getStart().equals(activity.getStart())
                && getEnd().equals(activity.getEnd())
                && getDescription().equals(activity.getDescription());

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = activityType.hashCode();
        result = 31 * result + getCountry().hashCode();
        result = 31 * result + getStart().hashCode();
        result = 31 * result + getEnd().hashCode();
        temp = Double.doubleToLongBits(getPrice());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getDescription().hashCode();
        return result;
    }
}
