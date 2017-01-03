package com.bnet.tnet;

import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.entities.EntitiesSamples;
import com.bnet.tnet.model.ActivitySearchFilter;
import com.bnet.tnet.model.BusinessSearchFilter;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SearchFilterTest {

    @Test
    public void searchBusiness() throws Exception {
        Business business = EntitiesSamples.makeBusiness();
        BusinessSearchFilter searchFilter = new BusinessSearchFilter();

        assertTrue(searchFilter.search(business, ""));
        assertTrue(searchFilter.search(business, "israel"));
        assertTrue(searchFilter.search(business, "email"));
        assertTrue(searchFilter.search(business, "website"));
        assertTrue(searchFilter.search(business, "555"));
        assertTrue(searchFilter.search(business, "usalem"));
        assertTrue(searchFilter.search(business, "hale"));
        assertFalse(searchFilter.search(business, "sgasdv"));
    }

    @Test
    public void searchActivity() throws Exception {
        Activity activity = EntitiesSamples.makeActivity();
        ActivitySearchFilter searchFilter = new ActivitySearchFilter();

        assertTrue(searchFilter.search(activity, ""));
        assertTrue(searchFilter.search(activity, "israel"));
        assertTrue(searchFilter.search(activity, "test"));
        assertTrue(searchFilter.search(activity, "travel"));
        assertTrue(searchFilter.search(activity, "21:00"));
        assertTrue(searchFilter.search(activity, "07/08"));
        assertTrue(searchFilter.search(activity, "53"));
        assertFalse(searchFilter.search(activity, "68.5"));
    }
}