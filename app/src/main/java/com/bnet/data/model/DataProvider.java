package com.bnet.data.model;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import com.bnet.data.model.backend.Providable;
import com.bnet.data.model.entities.Activity;
import com.bnet.data.model.entities.Business;

import java.util.ArrayList;

public class DataProvider extends ContentProvider {
    public static final String AUTHORITY = "com.bnet.provider";
    private static final ArrayList<Providable> providableList = new ArrayList<>();
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static void registerProvidable(Providable providable) {
        providableList.add(providable);
        uriMatcher.addURI(AUTHORITY, providable.getURIPath(), providableList.indexOf(providable));
    }

    @Override
    public boolean onCreate() {
        if (providableList.size() == 0) {
            registerProvidable(new Activity());
            registerProvidable(new Business());
        }

        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int id;

        Providable match = matchProvidable(uri);
        Providable item = match.fromContentValues(values);
        id = match.getRepository().addAndReturnAssignedId(item);

        return Uri.withAppendedPath(uri, "" + id);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Providable match = matchProvidable(uri);

        String[] columns = getMatrixColumns(match);
        Object[] row = new Object[columns.length];
        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (Object item : match.getRepository().getAll()) {
            ContentValues values = match.toContentValues((Providable) item);

            for (int i = 0; i < row.length; i++)
                row[i] = values.get(columns[i]);

            matrixCursor.addRow(row);
        }

        return matrixCursor;
    }

    private Providable matchProvidable(Uri uri) {
        if (uriMatcher.match(uri) >= providableList.size())
            throw new IllegalArgumentException("No Such Entity");

        return providableList.get(uriMatcher.match(uri));
    }

    public String[] getMatrixColumns(Providable providable) {
        ContentValues values = providable.toContentValues(providable);
        String [] columns = new String[values.size()];
        values.keySet().toArray(columns);
        return columns;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
