package com.bnet.shared.model.services.converters.contentvalues;

import android.content.ContentValues;

import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.services.converters.Converter;

public interface ContentValuesConverter<T extends Providable<T>> extends Converter<ContentValues,T> {
    ContentValues convert(T item);
    T convert(ContentValues contentValues);
}
