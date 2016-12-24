package com.bnet.shared.model;

import android.content.ContentValues;

import com.bnet.shared.model.entities.Address;
import com.bnet.shared.model.entities.Business;

import static com.bnet.shared.model.Constants.*;

public class BusinessContentValuesConverter implements ContentValuesConverter<Business>{
    public ContentValues convert(Business business) {
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
    public Business convert(ContentValues contentValues) throws Exception {
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
}
