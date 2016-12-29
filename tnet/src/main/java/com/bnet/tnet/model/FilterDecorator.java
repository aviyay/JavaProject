package com.bnet.tnet.model;

import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.backend.ProvidableRepository;

import java.util.ArrayList;
import java.util.List;

public class FilterDecorator<T extends Providable> implements ProvidableRepository<T> {
    private ProvidableRepository<T> repository;
    private Filter<T> filter;

    public FilterDecorator(ProvidableRepository<T> repository, Filter<T> filter) {
        this.repository = repository;
        this.filter = filter;
    }

    @Override
    public int addAndReturnAssignedId(T item) {
        return repository.addAndReturnAssignedId(item);
    }

    @Override
    public List<T> getAll() {
        return filter.filter(repository.getAll());
    }

    @Override
    public List<T> getAllNews() {
        return filter.filter(repository.getAllNews());
    }

    @Override
    public boolean isSomethingNew() {
        return repository.isSomethingNew();
    }

    @Override
    public void reset() {
        repository.reset();
    }

    @Override
    public T getOrNull(long id) {
        List<T> temp = new ArrayList<>();
        temp.add(repository.getOrNull(id));
        temp = filter.filter(temp);

        return temp.size() == 0? null : temp.get(0);
    }
}
