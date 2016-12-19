package com.bnet.shared.model.entities;

import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.ActivityType;
import com.bnet.shared.model.entities.Address;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.entities.DateTime;

import java.text.ParseException;

public class EntitiesSamples {
    private static Business business;
    private static Activity activity;

    static {
        initializeBusiness();

        initializeActivity();

    }

    private static void initializeActivity() {
        activity = new Activity();

        DateTime start = null;
        DateTime end = null;

        try {
            start = DateTime.parse("8:15 7/8/2016");
            end = DateTime.parse("21:00 7/8/2016");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        activity.setBusinessId(1);
        activity.setCountry("Israel");
        activity.setPrice(53.5);
        activity.setDescription("Test activity");
        activity.setType(ActivityType.AIRLINE);
        activity.setStart(start);
        activity.setEnd(end);
    }

    private static void initializeBusiness() {
        business = new Business();
        Address address = new Address();

        business.setName("Name");
        business.setEmail("email@gmail.com");
        business.setLinkToWebsite("www.website.com");
        business.setPhone("052-555-3322");

        address.setCountry("Israel");
        address.setCity("Jerusalem");
        address.setStreet("Havad Haleumi");

        business.setAddress(address);
    }

    public static Business getBusiness() {
        return business;
    }

    public static Activity getActivity() {
        return activity;
    }
}
