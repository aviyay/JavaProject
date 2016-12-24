package com.bnet.shared.model.services.converters.bundle;

import android.os.Bundle;

import com.bnet.shared.model.entities.Address;
import com.bnet.shared.model.entities.Business;

import static com.bnet.shared.model.Constants.*;

public class BusinessBundleConverter implements BundleConverter<Business>{

    @Override
    public Bundle convert(Business business) {
        Bundle result = new Bundle();

        result.putInt(BUSINESS_ID,business.getId());
        result.putString(BUSINESS_NAME,business.getName());
        result.putString(BUSINESS_EMAIL,business.getEmail());
        result.putString(BUSINESS_PHONE,business.getPhone());
        result.putString(BUSINESS_LINK,business.getLinkToWebsite());

        result.putString(ADDRESS_COUNTRY,business.getAddress().getCountry());
        result.putString(ADDRESS_CITY,business.getAddress().getCity());
        result.putString(ADDRESS_STREET,business.getAddress().getStreet());

        return result;
    }

    @Override
    public Business convert(Bundle bundle) {
        Business result = new Business();

        result.setId(bundle.getInt(BUSINESS_ID));
        result.setName(bundle.getString(BUSINESS_NAME));
        result.setEmail(bundle.getString(BUSINESS_EMAIL));
        result.setPhone(bundle.getString(BUSINESS_PHONE));
        result.setLinkToWebsite(bundle.getString(BUSINESS_LINK));

        Address address = new Address();
        address.setCountry(bundle.getString(ADDRESS_COUNTRY));
        address.setCity(bundle.getString(ADDRESS_CITY));
        address.setStreet(bundle.getString(ADDRESS_STREET));
        result.setAddress(address);
        return result;
    }
}
