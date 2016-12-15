package com.bnet.data.model.datasource;

import com.bnet.data.model.backend.ProvidableRepository;
import com.bnet.data.model.entities.Business;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ListProvidableRepositoryTest {
    ProvidableRepository<Business> repository;
    Business business;

    @Before
    public void setUpAndAddProvidable() throws Exception {
        repository = new ListProvidableRepository();
        business = new Business();
        repository.addAndReturnAssignedId(business);
    }

    @Test
    public void addAndCheckId() throws Exception {
        Business business2 = new Business();

        assertEquals(-1, business2.getId());
        int id = repository.addAndReturnAssignedId(business2);
        assertEquals(1, id); //0 was added in the setup method
    }

    @Test
    public void getAll() throws Exception {
        List<Business> businesses = repository.getAll();
        assertEquals(1, businesses.size());
        assertTrue(businesses.contains(business));
    }

    @Test
    public void getAllNews() throws Exception {
        List<Business> businesses = repository.getAllNews();
        assertEquals(1, businesses.size());
        assertTrue(businesses.contains(business));

        businesses = repository.getAllNews();
        assertEquals(0, businesses.size());

        Business business2 = new Business();
        repository.addAndReturnAssignedId(business2);
        businesses = repository.getAllNews();
        assertEquals(1, businesses.size());
        assertTrue(businesses.contains(business2));
    }

}