package com.bnet.shared.model.entities;

import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.ActivityType;
import com.bnet.shared.model.entities.Address;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.entities.DateTime;

import java.text.ParseException;

public class EntitiesSamples {
    private static Business makeBusiness() {
        Business business = new Business();
        Address address = new Address();

        business.setName("Business 1");
        business.setEmail("email@gmail.com");
        business.setLinkToWebsite("www.website.com");
        business.setPhone("052-555-3322");

        address.setCountry("Israel");
        address.setCity("Jerusalem");
        address.setStreet("Havad Haleumi");

        business.setAddress(address);

        return business;
    }

    private static Business makeBusiness2() {
        Business business = new Business();
        Address address = new Address();

        business.setName("Business 2");
        business.setEmail("email@gmail.com");
        business.setLinkToWebsite("www.website.com");
        business.setPhone("052-555-3322");

        address.setCountry("Israel");
        address.setCity("Jerusalem");
        address.setStreet("Havad Haleumi");

        business.setAddress(address);

        return business;
    }

    private static Business makeBusiness3() {
        Business business = new Business();
        Address address = new Address();

        business.setName("Business 3");
        business.setEmail("email@gmail.com");
        business.setLinkToWebsite("www.website.com");
        business.setPhone("052-555-3322");

        address.setCountry("Israel");
        address.setCity("Jerusalem");
        address.setStreet("Havad Haleumi");

        business.setAddress(address);

        return business;
    }

    private static Activity makeActivity() {
        Activity activity = new Activity();

        DateTime start = DateTime.parse("8:15 7/8/2016");
        DateTime end = DateTime.parse("21:00 7/8/2016");

        activity.setBusinessId(0);
        activity.setCountry("Israel");
        activity.setPrice(53.5);
        activity.setDescription("Test activity");
        activity.setType(ActivityType.TRAVEL);
        activity.setStart(start);
        activity.setEnd(end);

        return activity;
    }

    private static Activity makeActivity2() {
        Activity activity = new Activity();

        DateTime start = DateTime.parse("8:15 7/8/2016");
        DateTime end = DateTime.parse("21:00 7/8/2016");

        activity.setBusinessId(1);
        activity.setCountry("Israel");
        activity.setPrice(53.5);
        activity.setDescription("Test activity2");
        activity.setType(ActivityType.TRAVEL);
        activity.setStart(start);
        activity.setEnd(end);

        return activity;
    }

    private static Activity makeActivity3() {
        Activity activity = new Activity();

        DateTime start = DateTime.parse("8:15 7/8/2016");
        DateTime end = DateTime.parse("21:00 7/8/2016");

        activity.setBusinessId(2);
        activity.setCountry("Israel");
        activity.setPrice(53.5);
        activity.setDescription("Test activity3");
        activity.setType(ActivityType.AIRLINE);
        activity.setStart(start);
        activity.setEnd(end);

        return activity;
    }

    public static Business getBusiness() {
        return makeBusiness();
    }

    public static Business getBusiness2() {
        return makeBusiness2();
    }

    public static Business getNonAgencyBusiness() {
        return makeBusiness3();
    }

    public static Activity getActivity() {
        return makeActivity();
    }

    public static Activity getActivity2() {
        return makeActivity2();
    }

    public static Activity getNonTravelActivity() {
        return makeActivity3();
    }

}
