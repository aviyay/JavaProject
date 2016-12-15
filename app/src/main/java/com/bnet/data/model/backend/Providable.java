package com.bnet.data.model.backend;

import android.content.ContentValues;

import com.bnet.data.model.entities.Activity;

import java.util.List;

public interface Providable {
    int getId();
    void setId(int id);

    String getURIPath();

    ProvidableRepository getRepository();

    Providable fromContentValues(ContentValues contentValues);
}
