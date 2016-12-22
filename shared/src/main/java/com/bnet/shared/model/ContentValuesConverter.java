package com.bnet.shared.model;

import android.content.ContentValues;

import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.ActivityType;
import com.bnet.shared.model.entities.Address;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.entities.DateTime;

public class ContentValuesConverter {

    private static final String BUSINESS_ID = "_id";
    private static final String BUSINESS_NAME = "name";
    private static final String BUSINESS_EMAIL = "email";
    private static final String BUSINESS_PHONE = "phone";
    private static final String BUSINESS_LINK = "link";

    private static final String ADDRESS_COUNTRY = "country";
    private static final String ADDRESS_CITY = "city";
    private static final String ADDRESS_STREET = "street";

    private static final String ACTIVITY_ID = "_id";
    private static final String ACTIVITY_DESCRIPTION = "description";
    private static final String ACTIVITY_PRICE = "price";
    private static final String ACTIVITY_START = "start";
    private static final String ACTIVITY_END = "end";
    private static final String ACTIVITY_TYPE = "type";

    public static ContentValues businessToContentValues(Business business) {
        ContentValues result = new ContentValues();

        result.put(BUSINESS_ID,business.getId());
        result.put(BUSINESS_NAME,business.getName());
        result.put(BUSINESS_EMAIL,business.getEmail());
        result.put(BUSINESS_PHONE,business.getPhone());
        result.put(BUSINESS_LINK,business.getLinkToWebsite());

        result.put(ADDRESS_COUNTRY,business.getAddress().getCountry());
        result.put(ADDRESS_CITY,business.getAddress().getCity());
        result.put(ADDRESS_STREET,business.getAddress().getStreet());

        return result;
    }
    public static Business contentValuesToBusiness(ContentValues contentValues) throws Exception {
        Business result = new Business();

        result.setId(contentValues.getAsInteger(BUSINESS_ID));
        result.setName(contentValues.getAsString(BUSINESS_NAME));
        result.setEmail(contentValues.getAsString(BUSINESS_EMAIL));
        result.setPhone(contentValues.getAsString(BUSINESS_PHONE));
        result.setLinkToWebsite(contentValues.getAsString(BUSINESS_LINK));

        Address address = new Address();
        address.setCountry(contentValues.getAsString(ADDRESS_COUNTRY));
        address.setCity(contentValues.getAsString(ADDRESS_CITY));
        address.setStreet(contentValues.getAsString(ADDRESS_STREET));
        result.setAddress(address);
        return result;
    }

    public static ContentValues activityToContentValues(Activity activity) {
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
    public static Activity contentValuesToActivity(ContentValues contentValues) throws Exception{
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
