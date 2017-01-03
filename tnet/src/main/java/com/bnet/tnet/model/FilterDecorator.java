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
    public long addAndReturnAssignedId(T item) {
        return repository.addAndReturnAssignedId(item);
    }

    @Override
    public List<T> getAll() {
        return filterList(repository.getAll());
    }

    @Override
    public List<T> getAllNews() {
        return filterList(repository.getAllNews());
    }

    private List<T> filterList(List<T> input) {
        ArrayList<T> result = new ArrayList<>();

        for (T item : input)
            if (filter.isPass(item))
                result.add(item);

        return result;
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
        T result = repository.getOrNull(id);

        if (filter.isPass(result))
            return result;

        return null;
    }
}
