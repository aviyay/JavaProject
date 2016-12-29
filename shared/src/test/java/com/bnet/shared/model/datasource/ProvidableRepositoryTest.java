package com.bnet.shared.model.datasource;

import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.backend.ProvidableRepository;
import com.bnet.shared.model.entities.EntitiesSamples;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ProvidableRepositoryTest {
    private ProvidableRepository<Providable> repository;
    private Providable providable;
    private Providable providable2;


    @Test
    public void ListProvidableRepositoryTest() throws Exception {
        runTestsOn(new ListProvidableRepository<>());
    }

    private void runTestsOn(ProvidableRepository<Providable> repository) throws Exception {
        this.repository = repository;
        testRepository();
    }

    private void testRepository() throws Exception {
        businessConfig();
        runAllTests();
        activityConfig();
        runAllTests();
    }

    private void activityConfig() {
        providable = EntitiesSamples.makeActivity();
        providable2 = EntitiesSamples.makeActivity2();
    }

    private void businessConfig() throws Exception {
        providable = EntitiesSamples.makeBusiness();
        providable2 = EntitiesSamples.makeBusiness2();
    }


    private void runAllTests() throws Exception {
        freshStart();
        addAndCheckId();

        freshStart();
        getAll();

        freshStart();
        getAllNews();
    }

    private void freshStart() {
        repository.reset();
        providable.setId(-1);
        providable2.setId(-1);
    }

    private void addAndCheckId() throws Exception {
        assertEquals(-1, providable.getId());

        int id = repository.addAndReturnAssignedId(providable);
        assertEquals(0, id);
    }

    private void getAll() throws Exception {
        add(providable);

        assertSingle(repository.getAll(), providable);
    }

    private void getAllNews() throws Exception {
        addAndAssertSingleNews(providable);

        assertEquals(0, repository.getAllNews().size());

        addAndAssertSingleNews(providable2);
    }

    private void addAndAssertSingleNews(Providable providable) {
        add(providable);
        assertSingle(repository.getAllNews(), providable);
    }

    private void add(Providable providable) {
        repository.addAndReturnAssignedId(providable);
    }

    private void assertSingle(List<Providable> providableList, Providable expected) {
        assertEquals(1, providableList.size());
        assertTrue(providableList.contains(expected));
    }
}