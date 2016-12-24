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
    Converter converter;

    @Test
    public void BusinessContentValuesTest() throws Exception {
        converter = new BusinessContentValuesConverter();
        businessConvert();
    }

    @Test
    public void ActivityContentValuesTest() throws Exception {
        converter = new ActivityContentValuesConverter();
        activityConvert();
    }

    @Test
    public void BusinessBundleTest() throws Exception {
        converter = new BusinessBundleConverter();
        businessConvert();
    }

    @Test
    public void ActivityBundleTest() throws Exception {
        converter = new ActivityBundleConverter();
        activityConvert();
    }

    public void businessConvert() throws Exception {
        Business business = EntitiesSamples.getBusiness();

        Object converted = converter.convert(business);
        Business result = (Business) converter.convert(converted);

        assertEquals(business.getId(), result.getId());
        assertEquals(business, result);
    }

    public void activityConvert() throws Exception {
        Activity activity = EntitiesSamples.getActivity();

        Object converted = converter.convert(activity);
        Activity result = (Activity) converter.convert(converted);

        assertEquals(activity.getId(), result.getId());
        assertEquals(activity,result);
    }
}