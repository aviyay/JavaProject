package com.bnet.shared.model.services.converters.contentvalues;

import android.content.ContentValues;

import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.ActivityType;
import com.bnet.shared.model.entities.DateTime;
import com.bnet.shared.model.services.converters.Converter;

import static com.bnet.shared.model.Constants.ActivityContract.*;

public class ActivityContentValuesConverter implements Converter<ContentValues, Activity> {
    public ContentValues convert(Activity activity) {
        ContentValues result = new ContentValues();

        result.put(ID, activity.getId());
        result.put(COUNTRY, activity.getCountry());
        result.put(BUSINESS_ID, activity.getBusinessId());
        result.put(DESCRIPTION, activity.getDescription());
        result.put(PRICE, activity.getPrice());
        result.put(START, activity.getStart().toString());
        result.put(END, activity.getEnd().toString());
        result.put(TYPE, String.valueOf(activity.getType()));

        return result;
    }

    public Activity convertBack(ContentValues contentValues) {
        Activity result = new Activity();

        result.setId(contentValues.getAsInteger(ID));
        result.setCountry(contentValues.getAsString(COUNTRY));
        result.setBusinessId(contentValues.getAsInteger(BUSINESS_ID));
        result.setDescription(contentValues.getAsString(DESCRIPTION));
        result.setPrice(contentValues.getAsDouble(PRICE));
        result.setStart(DateTime.parse(contentValues.getAsString(START)));
        result.setEnd(DateTime.parse(contentValues.getAsString(END)));
        result.setType(ActivityType.valueOf(contentValues.getAsString(TYPE)));

        return result;
    }
}
