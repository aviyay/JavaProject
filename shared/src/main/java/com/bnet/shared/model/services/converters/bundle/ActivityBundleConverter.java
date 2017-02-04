package com.bnet.shared.model.services.converters.bundle;

import android.os.Bundle;

import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.ActivityType;
import com.bnet.shared.model.entities.DateTime;
import com.bnet.shared.model.services.converters.Converter;

import static com.bnet.shared.model.Constants.ActivityContract.*;

public class ActivityBundleConverter implements Converter<Bundle, Activity> {
    /**
     * Convert Activity to Bundle
     * @param activity activity to be converted
     * @return The converted Bundle
     */
    @Override
    public Bundle convert(Activity activity) {
        Bundle result = new Bundle();

        result.putLong(ID, activity.getId());
        result.putString(COUNTRY, activity.getCountry());
        result.putLong(BUSINESS_ID, activity.getBusinessId());
        result.putString(DESCRIPTION, activity.getDescription());
        result.putDouble(PRICE, activity.getPrice());
        result.putString(START, activity.getStart().toString());
        result.putString(END, activity.getEnd().toString());
        result.putString(TYPE, String.valueOf(activity.getType()));

        return result;
    }

    /**
     * Convert Bundle to Activity
     * @param bundle The Bundle to be converted
     * @return The converted activity
     */
    @Override
    public Activity convertBack(Bundle bundle) {
        Activity result = new Activity();

        result.setId(bundle.getLong(ID));
        result.setCountry(bundle.getString(COUNTRY));
        result.setBusinessId(bundle.getLong(BUSINESS_ID));
        result.setDescription(bundle.getString(DESCRIPTION));
        result.setPrice(bundle.getDouble(PRICE));
        result.setStart(DateTime.parse(bundle.getString(START)));
        result.setEnd(DateTime.parse(bundle.getString(END)));
        result.setType(ActivityType.valueOf(bundle.getString(TYPE)));

        return result;
    }
}
