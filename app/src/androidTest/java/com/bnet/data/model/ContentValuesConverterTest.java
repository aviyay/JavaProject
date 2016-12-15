package com.bnet.data.model;

import android.content.ContentValues;

import com.bnet.data.model.entities.Activity;
import com.bnet.data.model.entities.Business;
import com.bnet.data.model.entities.EntitiesSamples;

import org.junit.Test;

import static org.junit.Assert.*;

public class ContentValuesConverterTest {
    @Test
    public void businessToContentValuesAndBack() throws Exception {
        Business business = EntitiesSamples.getBusiness();

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
        Activity activity = EntitiesSamples.getActivity();

        ContentValues contentValues = ContentValuesConverter.activityToContentValues(activity);
        Activity result = ContentValuesConverter.contentValuesToActivity(contentValues);

        assertEquals(activity.getId(), result.getId());
        assertEquals(activity.getCountry(), result.getCountry());
        assertEquals(activity.getBusinessId(), result.getBusinessId());
        assertEquals(activity.getDescription(), result.getDescription());
        assertEquals(activity.getPrice(), result.getPrice(), 0.00001);
        assertEquals(activity.getType(), result.getType());
        assertEquals(activity.getStart(), result.getStart());
        assertEquals(activity.getEnd(), result.getEnd());
    }
}