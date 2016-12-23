package com.bnet.shared.model.backend;

import android.content.ContentValues;

public interface Providable<T extends Providable> {
    int getId();
    void setId(int id);

    T fromContentValues(ContentValues contentValues);
    ContentValues toContentValues(T item);
}
