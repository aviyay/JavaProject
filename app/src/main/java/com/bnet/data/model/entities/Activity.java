package com.bnet.data.model.entities;

import android.content.ContentValues;
import android.database.Cursor;

import com.bnet.data.model.ContentValuesConverter;
import com.bnet.data.model.backend.Providable;
import com.bnet.data.model.backend.ProvidableRepository;
import com.bnet.data.model.backend.RepositoriesFactory;

import java.text.ParseException;

public class Activity implements Providable<Activity> {
    private int id = -1;
    private ActivityType activityType;
    private String country;
    private DateTime start = new DateTime();
    private DateTime end = new DateTime();
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

    @Override
    public ContentValues toContentValues(Activity item) {
        return ContentValuesConverter.activityToContentValues(item);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;

        if (getId() != activity.getId()) return false;
        if (Double.compare(activity.getPrice(), getPrice()) != 0) return false;
        if (getBusinessId() != activity.getBusinessId()) return false;
        if (activityType != activity.activityType) return false;
        if (!getCountry().equals(activity.getCountry())) return false;
        if (!getStart().equals(activity.getStart())) return false;
        if (!getEnd().equals(activity.getEnd())) return false;
        return getDescription().equals(activity.getDescription());

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getId();
        result = 31 * result + activityType.hashCode();
        result = 31 * result + getCountry().hashCode();
        result = 31 * result + getStart().hashCode();
        result = 31 * result + getEnd().hashCode();
        temp = Double.doubleToLongBits(getPrice());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getBusinessId();
        return result;
    }
}
