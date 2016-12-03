package com.students.aviyay_and_shy.bnet.model;

import android.content.ContentValues;

import com.students.aviyay_and_shy.bnet.model.entities.Activity;
import com.students.aviyay_and_shy.bnet.model.entities.ActivityType;
import com.students.aviyay_and_shy.bnet.model.entities.Address;
import com.students.aviyay_and_shy.bnet.model.entities.Business;
import com.students.aviyay_and_shy.bnet.model.entities.DateTime;

import org.junit.Test;

import static org.junit.Assert.*;

public class ContentValuesConverterTest {
    @Test
    public void businessToContentValuesAndBack() throws Exception {
        Business business = new Business();
        Address address = new Address();

        business.setId(1);
        business.setName("Name");
        business.setEmail("email@gmail.com");
        business.setLinkToWebsite("www.website.com");
        business.setPhone("052-555-3322");

        address.setCountry("Israel");
        address.setCity("Jerusalem");
        address.setStreet("Havad Haleumi");

        business.setAddress(address);

        ContentValues contentValues = ContentValuesConverter.businessToContentValues(business);
        Business result = ContentValuesConverter.contentValuesToBusiness(contentValues);

        assertEquals(business.getId(), result.getId());
        assertEquals(business.getName(), result.getName());
        assertEquals(business.getEmail(), result.getEmail());
        assertEquals(business.getLinkToWebsite(), result.getLinkToWebsite());
        assertEquals(business.getPhone(), result.getPhone());

        assertEquals(business.getAddress().getCountry(),result.getAddress().getCountry());
        assertEquals(business.getAddress().getCity(), result.getAddress().getCity());
        assertEquals(business.getAddress().getStreet(),result.getAddress().getStreet());
    }

    @Test
    public void activityToContentValuesAndBack() throws Exception {
        Activity activity = new Activity();
        DateTime start = DateTime.parse("8:15 7/8/2016");
        DateTime end = DateTime.parse("21:00 7/8/2016");

        activity.setBusinessId(1);
        activity.setCountry("Israel");
        activity.setPrice(53.5);
        activity.setDescription("Test activity");
        activity.setType(ActivityType.AIRLINE);
        activity.setStart(start);
        activity.setEnd(end);

        ContentValues contentValues = ContentValuesConverter.activityToContentValues(activity);
        Activity result = ContentValuesConverter.contentValuesToActivity(contentValues);

        assertEquals(activity.getCountry(), result.getCountry());
        assertEquals(activity.getBusinessId(), result.getBusinessId());
        assertEquals(activity.getDescription(), result.getDescription());
        assertEquals(activity.getPrice(), result.getPrice(), 0.00001);
        assertEquals(activity.getType(), result.getType());
        assertEquals(activity.getStart(), result.getStart());
        assertEquals(activity.getEnd(), result.getEnd());
    }
}