package com.bnet.shared.model;

import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.entities.EntitiesSamples;
import com.bnet.shared.model.services.converters.bundle.ActivityBundleConverter;
import com.bnet.shared.model.services.converters.contentvalues.ActivityContentValuesConverter;
import com.bnet.shared.model.services.converters.bundle.BusinessBundleConverter;
import com.bnet.shared.model.services.converters.contentvalues.BusinessContentValuesConverter;
import com.bnet.shared.model.services.converters.Converter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class ConverterTest {

    @Test
    public void BusinessContentValuesTest() throws Exception {
        businessConvertAndBack(new BusinessContentValuesConverter());
    }

    @Test
    public void ActivityContentValuesTest() throws Exception {
        activityConvertAndBack(new ActivityContentValuesConverter());
    }

    @Test
    public void BusinessBundleTest() throws Exception {
        businessConvertAndBack(new BusinessBundleConverter());
    }

    @Test
    public void ActivityBundleTest() throws Exception {
        activityConvertAndBack(new ActivityBundleConverter());
    }

    private <T> void businessConvertAndBack(Converter<T, Business> converter) {
        Business business = EntitiesSamples.makeBusiness();

        T converted = converter.convert(business);
        Business result = converter.convertBack(converted);

        assertEquals(business.getId(), result.getId());
        assertEquals(business, result);
    }

    private <T> void activityConvertAndBack(Converter<T, Activity> converter) {
        Activity activity = EntitiesSamples.makeActivity();

        T converted = converter.convert(activity);
        Activity result = converter.convertBack(converted);

        assertEquals(activity.getId(), result.getId());
        assertEquals(activity,result);
    }
}