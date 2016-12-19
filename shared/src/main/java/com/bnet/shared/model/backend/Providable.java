package com.bnet.shared.model.backend;

import android.content.ContentValues;

public interface Providable<T extends Providable> {
    int getId();
    void setId(int id);

    String getURIPath();

    ProvidableRepository<T> getRepository();

    T fromContentValues(ContentValues contentValues);
    ContentValues toContentValues(T item);
}
