package com.bnet.tnet.model;

public abstract class SearchFilter<T> implements Filter<T> {
    protected String searchText = "";

    public void setSearchText(String searchText) {
        this.searchText = searchText.toLowerCase();
    }

    @Override
    public boolean isPass(T item) {
        return search(item);
    }

    public abstract boolean search(T item);

    public boolean search(T item, String searchText) {
        setSearchText(searchText);
        return search(item);
    }
}
