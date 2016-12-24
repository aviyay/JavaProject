package com.bnet.shared.model;

import android.content.ContentValues;

public interface ContentValuesConverter<T> {
    ContentValues convert(T item);
    T convert(ContentValues contentValues) throws Exception;
}
