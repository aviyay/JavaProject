package com.bnet.shared.model.services.converters;

import com.bnet.shared.model.backend.Providable;

public interface Converter<Result, T extends Providable> {
    Result convert(T item);
    T convert(Result result);
}
