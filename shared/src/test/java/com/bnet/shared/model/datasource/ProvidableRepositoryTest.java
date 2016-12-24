package com.bnet.shared.model.datasource;

import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.backend.ProvidableRepository;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.entities.EntitiesSamples;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ProvidableRepositoryTest {
    private ProvidableRepository repository;
    private Providable providable;
    private Providable providable2;


    @Test
    public void ListProvidableRepositoryTest() throws Exception {
        repository = new ListProvidableRepository();
        testRepository();
    }

    private void testRepository() throws Exception {
        businessConfig();
        runAllTests();
        activityConfig();
        runAllTests();
    }

    private void activityConfig() {
        repository.reset();
        providable = EntitiesSamples.getActivity();
        providable2 = EntitiesSamples.getActivity2();
    }

    private void businessConfig() throws Exception {
        repository.reset();
        providable = EntitiesSamples.getBusiness();
        providable2 = EntitiesSamples.getBusiness2();
    }


    private void runAllTests() throws Exception {
        addAndCheckId();
        getAll();
        getAllNews();
    }


    public void addAndCheckId() throws Exception {
        assertEquals(-1, providable.getId());
        int id = repository.addAndReturnAssignedId(providable);
        assertEquals(0, id);
    }

    public void getAll() throws Exception {
        List<Providable> providableList = repository.getAll();
        assertEquals(1, providableList.size());
        assertTrue(providableList.contains(providable));
    }

    public void getAllNews() throws Exception {
        List<Providable> providableList = repository.getAllNews();
        assertEquals(1, providableList.size());
        assertTrue(providableList.contains(providable));

        providableList = repository.getAllNews();
        assertEquals(0, providableList.size());

        repository.addAndReturnAssignedId(providable2);
        providableList = repository.getAllNews();
        assertEquals(1, providableList.size());
        assertTrue(providableList.contains(providable2));
    }

}