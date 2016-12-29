package com.bnet.shared.model.services.converters;

public interface Converter<Result, T> {
    Result convert(T item);
    T convertBack(Result result);
}
