package com.bnet.shared.model.services.converters.bundle;

import android.os.Bundle;

import com.bnet.shared.model.entities.Address;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.services.converters.Converter;

import static com.bnet.shared.model.Constants.BusinessContract.*;

public class BusinessBundleConverter implements Converter<Bundle, Business> {
    /**
     * Convert Business to Bundle
     * @param business business to be converted
     * @return The converted Bundle
     */
    @Override
    public Bundle convert(Business business) {
        Bundle result = new Bundle();

        result.putLong(ID, business.getId());
        result.putString(NAME, business.getName());
        result.putString(EMAIL, business.getEmail());
        result.putString(PHONE, business.getPhone());
        result.putString(LINK, business.getLinkToWebsite());

        result.putString(ADDRESS_COUNTRY, business.getAddress().getCountry());
        result.putString(ADDRESS_CITY, business.getAddress().getCity());
        result.putString(ADDRESS_STREET, business.getAddress().getStreet());

        return result;
    }

    /**
     * Convert Bundle to Business
     * @param bundle The Bundle to be converted
     * @return The converted business
     */
    @Override
    public Business convertBack(Bundle bundle) {
        Business result = new Business();

        result.setId(bundle.getLong(ID));
        result.setName(bundle.getString(NAME));
        result.setEmail(bundle.getString(EMAIL));
        result.setPhone(bundle.getString(PHONE));
        result.setLinkToWebsite(bundle.getString(LINK));

        Address address = new Address();
        address.setCountry(bundle.getString(ADDRESS_COUNTRY));
        address.setCity(bundle.getString(ADDRESS_CITY));
        address.setStreet(bundle.getString(ADDRESS_STREET));
        result.setAddress(address);
        return result;
    }
}
