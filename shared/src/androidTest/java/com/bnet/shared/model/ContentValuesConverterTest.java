package com.bnet.shared.model;

import android.content.ContentValues;

import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.entities.EntitiesSamples;

import org.junit.Test;
import static org.junit.Assert.*;


public class ContentValuesConverterTest {
    @Test
    public void businessToContentValuesAndBack() throws Exception {
        Business business = EntitiesSamples.getBusiness();

        ContentValues contentValues = ProvidableUtils.contentValuesConvert(business);
        Business result = (Business) ProvidableUtils.contentValuesConvert(Business.class, contentValues);

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

        ContentValues contentValues = ProvidableUtils.contentValuesConvert(activity);
        Activity result = (Activity) ProvidableUtils.contentValuesConvert(Activity.class, contentValues);

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