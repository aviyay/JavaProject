package com.bnet.shared.model.entities;

import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.ActivityType;
import com.bnet.shared.model.entities.Address;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.entities.DateTime;

import java.text.ParseException;

public class EntitiesSamples {
    private static Business business;
    private static Business business2;
    private static Business business3;
    private static Activity activity;
    private static Activity activity2;
    private static Activity activity3;

    static {
        initializeBusiness();
        initializeBusiness2();
        initializeBusiness3();

        initializeActivity();
        initializeActivity2();
        initializeActivity3();

    }

    private static void initializeBusiness() {
        business = new Business();
        Address address = new Address();

        business.setName("Business 1");
        business.setEmail("email@gmail.com");
        business.setLinkToWebsite("www.website.com");
        business.setPhone("052-555-3322");

        address.setCountry("Israel");
        address.setCity("Jerusalem");
        address.setStreet("Havad Haleumi");

        business.setAddress(address);
    }

    private static void initializeBusiness2() {
        business2 = new Business();
        Address address = new Address();

        business2.setName("Business 2");
        business2.setEmail("email@gmail.com");
        business2.setLinkToWebsite("www.website.com");
        business2.setPhone("052-555-3322");

        address.setCountry("Israel");
        address.setCity("Jerusalem");
        address.setStreet("Havad Haleumi");

        business2.setAddress(address);
    }

    private static void initializeBusiness3() {
        business3 = new Business();
        Address address = new Address();

        business3.setName("Business 3");
        business3.setEmail("email@gmail.com");
        business3.setLinkToWebsite("www.website.com");
        business3.setPhone("052-555-3322");

        address.setCountry("Israel");
        address.setCity("Jerusalem");
        address.setStreet("Havad Haleumi");

        business3.setAddress(address);
    }

    private static void initializeActivity() {
        activity = new Activity();

        DateTime start = null;
        DateTime end = null;

        try {
            start = DateTime.parse("8:15 7/8/2016");
            end = DateTime.parse("21:00 7/8/2016");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        activity.setBusinessId(1);
        activity.setCountry("Israel");
        activity.setPrice(53.5);
        activity.setDescription("Test activity");
        activity.setType(ActivityType.TRAVEL);
        activity.setStart(start);
        activity.setEnd(end);
    }

    private static void initializeActivity2() {
        activity2 = new Activity();

        DateTime start = null;
        DateTime end = null;

        try {
            start = DateTime.parse("8:15 7/8/2016");
            end = DateTime.parse("21:00 7/8/2016");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        activity2.setBusinessId(1);
        activity2.setCountry("Israel");
        activity2.setPrice(53.5);
        activity2.setDescription("Test activity2");
        activity2.setType(ActivityType.TRAVEL);
        activity2.setStart(start);
        activity2.setEnd(end);
    }

    private static void initializeActivity3() {
        activity3 = new Activity();

        DateTime start = null;
        DateTime end = null;

        try {
            start = DateTime.parse("8:15 7/8/2016");
            end = DateTime.parse("21:00 7/8/2016");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        activity3.setBusinessId(1);
        activity3.setCountry("Israel");
        activity3.setPrice(53.5);
        activity3.setDescription("Test activity3");
        activity3.setType(ActivityType.TRAVEL);
        activity3.setStart(start);
        activity3.setEnd(end);
    }

    public static Business getBusiness() {
        return business;
    }
    public static Business getBusiness2() {return business2; }
    public static Business getBusiness3() {
        return business3;
    }

    public static Activity getActivity() {
        return activity;
    }
    public static Activity getActivity2() {
        return activity2;
    }
    public static Activity getActivity3() {
        return activity3;
    }

}
