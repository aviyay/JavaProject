package com.bnet.shared.model;

import android.content.ContentValues;

import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.ActivityType;
import com.bnet.shared.model.entities.DateTime;

import static com.bnet.shared.model.Constants.*;

public class ActivityContentValuesConverter implements ContentValuesConverter<Activity>{
    public ContentValues convert(Activity activity) {
        ContentValues result = new ContentValues();

        result.put(ACTIVITY_ID, activity.getId());
        result.put(ADDRESS_COUNTRY, activity.getCountry());
        result.put(BUSINESS_ID, activity.getBusinessId());
        result.put(ACTIVITY_DESCRIPTION, activity.getDescription());
        result.put(ACTIVITY_PRICE, activity.getPrice());
        result.put(ACTIVITY_START, activity.getStart().format());
        result.put(ACTIVITY_END, activity.getEnd().format());
        result.put(ACTIVITY_TYPE, String.valueOf(activity.getType()));

        return result;
    }
    public Activity convert(ContentValues contentValues) throws Exception{
        Activity result = new Activity();

        result.setId(contentValues.getAsInteger(ACTIVITY_ID));
        result.setCountry(contentValues.getAsString(ADDRESS_COUNTRY));
        result.setBusinessId(contentValues.getAsInteger(BUSINESS_ID));
        result.setDescription(contentValues.getAsString(ACTIVITY_DESCRIPTION));
        result.setPrice(contentValues.getAsDouble(ACTIVITY_PRICE));
        result.setStart(DateTime.parse(contentValues.getAsString(ACTIVITY_START)));
        result.setEnd(DateTime.parse(contentValues.getAsString(ACTIVITY_END)));
        result.setType(ActivityType.valueOf(contentValues.getAsString(ACTIVITY_TYPE)));

        return result;
    }
}
