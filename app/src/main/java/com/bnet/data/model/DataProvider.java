package com.bnet.data.model;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import com.bnet.data.model.backend.Database;
import com.bnet.data.model.backend.DatabaseFactory;
import com.bnet.data.model.entities.Activity;
import com.bnet.data.model.entities.Business;

import java.util.List;

public class DataProvider extends ContentProvider {
    Database db = DatabaseFactory.getDatabase();

    public static final String AUTHORITY = "com.bnet.provider";

    private static final int ACTIVITIES = 1;
    private static final int BUSINESSES = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, "activities", ACTIVITIES);
        uriMatcher.addURI(AUTHORITY, "businesses", BUSINESSES);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case ACTIVITIES:
                return activitiesToCursor();
            case BUSINESSES:
                return businessesToCursor();
            default:
                return null;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private Cursor activitiesToCursor() {
        String [] columns = {"_id", "business_id", "country", "description", "price", "start", "end", "type"};

        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (Activity a : db.getAllActivities()) {
            matrixCursor.addRow(new Object[]{
                    a.getId(), a.getBusinessId(), a.getCountry(), a.getDescription(),
                    a.getPrice(), a.getStart(), a.getEnd(), a.getType()});
        }

        return matrixCursor;
    }
    private Cursor businessesToCursor() {
        String [] columns = {"_id", "name", "address", "email", "phone", "link"};

        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (Business b : db.getAllBusinesses()) {
            matrixCursor.addRow(new Object[]{
                    b.getId(), b.getName(), b.getAddress(), b.getEmail(),
                    b.getPhone(), b.getLinkToWebsite()});
        }

        return matrixCursor;
    }
}
