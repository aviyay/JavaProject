package com.bnet.tnet.model;

public interface Filter<T> {
    /**
     * Is The item pass the Filter Test
     * @param item The item to check
     * @return Whether the item passed the test
     */
    boolean isPass(T item);
}
