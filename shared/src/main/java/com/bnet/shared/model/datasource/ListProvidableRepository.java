package com.bnet.shared.model.datasource;

import android.util.Log;

import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.backend.ProvidableRepository;

import java.util.ArrayList;
import java.util.List;

public class ListProvidableRepository<T extends Providable> implements ProvidableRepository<T> {
    private List<T> items = new ArrayList<>();
    private ArrayList<T> news = new ArrayList<>();

    public ListProvidableRepository() {
        //Log.d("MyCustomTag", "Shared: ListProvidableRepository " + toString() + " created");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
       // Log.d("MyCustomTag", "Shared: ListProvidableRepository " + toString() + " destroyed");
    }

    @Override
    public int addAndReturnAssignedId(T item) {
        items.add(item);
        news.add(item);

        item.setId(items.indexOf(item));
        return item.getId();
    }

    @Override
    public List<T> getAll() {
        news.clear();
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
    public T getOrNull(long id) {
        for (T p : items)
            if (p.getId() == id)
                return p;

        return null;
    }

    @Override
    public boolean isSomethingNew() {
        return news.size() > 0;
    }
}
