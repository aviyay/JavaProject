package com.bnet.shared.model.services.converters.bundle;

import android.os.Bundle;

import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.ActivityType;
import com.bnet.shared.model.entities.DateTime;

import static com.bnet.shared.model.Constants.ACTIVITY_DESCRIPTION;
import static com.bnet.shared.model.Constants.ACTIVITY_END;
import static com.bnet.shared.model.Constants.ACTIVITY_ID;
import static com.bnet.shared.model.Constants.ACTIVITY_PRICE;
import static com.bnet.shared.model.Constants.ACTIVITY_START;
import static com.bnet.shared.model.Constants.ACTIVITY_TYPE;
import static com.bnet.shared.model.Constants.ADDRESS_COUNTRY;
import static com.bnet.shared.model.Constants.BUSINESS_ID;

public class ActivityBundleConverter implements BundleConverter<Activity> {
    @Override
    public Bundle convert(Activity activity) {
        Bundle result = new Bundle();

        result.putInt(ACTIVITY_ID, activity.getId());
        result.putString(ADDRESS_COUNTRY, activity.getCountry());
        result.putInt(BUSINESS_ID, activity.getBusinessId());
        result.putString(ACTIVITY_DESCRIPTION, activity.getDescription());
        result.putDouble(ACTIVITY_PRICE, activity.getPrice());
        result.putString(ACTIVITY_START, activity.getStart().format());
        result.putString(ACTIVITY_END, activity.getEnd().format());
        result.putString(ACTIVITY_TYPE, String.valueOf(activity.getType()));

        return result;
    }

    @Override
    public Activity convert(Bundle bundle) {
        Activity result = new Activity();

        result.setId(bundle.getInt(ACTIVITY_ID));
        result.setCountry(bundle.getString(ADDRESS_COUNTRY));
        result.setBusinessId(bundle.getInt(BUSINESS_ID));
        result.setDescription(bundle.getString(ACTIVITY_DESCRIPTION));
        result.setPrice(bundle.getDouble(ACTIVITY_PRICE));
        result.setStart(DateTime.parse(bundle.getString(ACTIVITY_START)));
        result.setEnd(DateTime.parse(bundle.getString(ACTIVITY_END)));
        result.setType(ActivityType.valueOf(bundle.getString(ACTIVITY_TYPE)));

        return result;
    }
}
