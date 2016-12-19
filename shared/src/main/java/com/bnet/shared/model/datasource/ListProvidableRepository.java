package com.bnet.shared.model.datasource;

import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.backend.ProvidableRepository;

import java.util.ArrayList;
import java.util.List;

public class ListProvidableRepository<T extends Providable> implements ProvidableRepository<T> {
    private List<T> items = new ArrayList<>();
    private ArrayList<T> news = new ArrayList<>();

    @Override
    public int addAndReturnAssignedId(T item) {
        items.add(item);
        news.add(item);

        item.setId(items.indexOf(item));
        return item.getId();
    }

    @Override
    public List<T> getAll() {
        return items;
    }

    @Override
    public List<T> getAllNews() {
        ArrayList<T> result = (ArrayList<T>) news.clone();
        news.clear();
        return result;
    }

    @Override
    public void reset() {
        items.clear();
        news.clear();
    }

    @Override
    public boolean isSomethingNew() {
        return news.size() > 0;
    }
}
