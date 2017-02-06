package com.bnet.tnet.model;

public abstract class SearchFilter<T> implements Filter<T> {
    String searchText = "";

    /**
     * Set current Search text
     * @param searchText The Text to be set as the Search Text
     */
    public void setSearchText(String searchText) {
        this.searchText = searchText.toLowerCase();
    }

    @Override
    public boolean isPass(T item) {
        return search(item);
    }

    /**
     * Perform search operation on the item
     * @param item The item to be searched
     * @return Whether the item passed the search operation or not
     */
    public abstract boolean search(T item);

    /**
     * Perform search on the item, with the given string
     * @param item The item to be searched
     * @param searchText The string to search in the item
     * @return Whether the item passed the search or not
     */
    public boolean search(T item, String searchText) {
        setSearchText(searchText);
        return search(item);
    }
}
