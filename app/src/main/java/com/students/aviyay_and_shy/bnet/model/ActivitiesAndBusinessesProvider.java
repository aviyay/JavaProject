package com.students.aviyay_and_shy.bnet.model;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.students.aviyay_and_shy.bnet.model.backend.Database;
import com.students.aviyay_and_shy.bnet.model.backend.DatabaseFactory;


public class ActivitiesAndBusinessesProvider extends ContentProvider {
    private Database db;

    @Override
    public boolean onCreate() {
        db = DatabaseFactory.getDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
