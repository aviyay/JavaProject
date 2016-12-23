package com.bnet.tnet;

import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.entities.EntitiesSamples;
import com.bnet.tnet.model.Searcher;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by שי on 23/12/2016.
 */
public class SearcherTest {
    @Test
    public void search() throws Exception {
        Business business = EntitiesSamples.getBusiness();
        assertEquals(true, Searcher.Search(business,"israel"));
        assertEquals(true, Searcher.Search(business,"email"));
        assertEquals(true, Searcher.Search(business,"website"));
        assertEquals(true, Searcher.Search(business,"555"));
        assertEquals(true, Searcher.Search(business,"usalem"));
        assertEquals(true, Searcher.Search(business,"hale"));
        assertEquals(false, Searcher.Search(business,"sgasdv"));
        Activity activity=EntitiesSamples.getActivity();
        assertEquals(true, Searcher.Search(activity,"israel"));
        assertEquals(true, Searcher.Search(activity,"test"));
        assertEquals(true, Searcher.Search(activity,"travel"));
        assertEquals(true, Searcher.Search(activity,"21:00"));
        assertEquals(true, Searcher.Search(activity,"07/08"));
        assertEquals(true, Searcher.Search(activity,"53"));
        assertEquals(false, Searcher.Search(activity,"68.5"));
    }

    @Test
    public void filterList() throws Exception {

        ArrayList<Business> bis=new ArrayList<>();
        bis.add(EntitiesSamples.getBusiness());
        bis.add(EntitiesSamples.getBusiness2());
        bis.add(EntitiesSamples.getBusiness3());
        assertEquals(0, Searcher.FilterBusiness(bis,"isradel").size());
        assertEquals(3, Searcher.FilterBusiness(bis,"israel").size());
        assertEquals(3, Searcher.FilterBusiness(bis,"email").size());
        assertEquals(1, Searcher.FilterBusiness(bis,"businesS 1").size());




    }

}