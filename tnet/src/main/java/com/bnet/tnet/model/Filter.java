package com.bnet.tnet.model;

public interface Filter<T> {
    boolean isPass(T item);
}
