package com.bnet.data.model.backend;

import android.content.ContentValues;
import android.database.Cursor;

import java.text.ParseException;
import java.util.List;

public interface Providable<T extends Providable> {
    int getId();
    void setId(int id);

    String getURIPath();

    ProvidableRepository<T> getRepository();

    T fromContentValues(ContentValues contentValues);
    ContentValues toContentValues(T item);
}
