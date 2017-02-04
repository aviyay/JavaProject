package com.bnet.shared.model.services.converters.contentvalues;

import android.content.ContentValues;

import com.bnet.shared.model.entities.Address;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.services.converters.Converter;

import static com.bnet.shared.model.Constants.BusinessContract.*;

public class BusinessContentValuesConverter implements Converter<ContentValues, Business> {
    /**
     * Convert Business to Content Values
     * @param business business to be converted
     * @return The converted Content values
     */
    public ContentValues convert(Business business) {
        ContentValues result = new ContentValues();

        result.put(ID, business.getId());
        result.put(NAME, business.getName());
        result.put(EMAIL, business.getEmail());
        result.put(PHONE, business.getPhone());
        result.put(LINK, business.getLinkToWebsite());

        result.put(ADDRESS_COUNTRY, business.getAddress().getCountry());
        result.put(ADDRESS_CITY, business.getAddress().getCity());
        result.put(ADDRESS_STREET, business.getAddress().getStreet());

        return result;
    }

    /**
     * Convert Content Values to Business
     * @param contentValues The content values to be converted
     * @return The converted business
     */
    public Business convertBack(ContentValues contentValues) {
        Business result = new Business();

        result.setId(contentValues.getAsLong(ID));
        result.setName(contentValues.getAsString(NAME));
        result.setEmail(contentValues.getAsString(EMAIL));
        result.setPhone(contentValues.getAsString(PHONE));
        result.setLinkToWebsite(contentValues.getAsString(LINK));

        Address address = new Address();
        address.setCountry(contentValues.getAsString(ADDRESS_COUNTRY));
        address.setCity(contentValues.getAsString(ADDRESS_CITY));
        address.setStreet(contentValues.getAsString(ADDRESS_STREET));
        result.setAddress(address);
        return result;
    }
}
