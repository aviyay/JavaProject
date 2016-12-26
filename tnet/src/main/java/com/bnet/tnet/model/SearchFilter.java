package com.bnet.tnet.model;

import java.util.ArrayList;
import java.util.List;

public abstract class SearchFilter<T> implements Filter<T> {
    protected String searchText = "";

    public void setSearchText(String searchText) {
        this.searchText = searchText.toLowerCase();
    }

    public List<T> filter(List<T> input, String searchText) {
        setSearchText(searchText);
        return filter(input);
    }

    @Override
    public List<T> filter(List<T> input) {
        ArrayList<T> array = new ArrayList<>();
        for (T item : input)
            if (search(item))
                array.add(item);
        return array;
    }

    public abstract boolean search(T item);

    public boolean search(T item, String searchText) {
        setSearchText(searchText);
        return search(item);
    }
}
