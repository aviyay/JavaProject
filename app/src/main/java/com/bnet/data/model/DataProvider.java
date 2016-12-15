package com.bnet.data.model;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import com.bnet.data.model.backend.Database;
import com.bnet.data.model.backend.DatabaseFactory;
import com.bnet.data.model.backend.Providable;
import com.bnet.data.model.entities.Activity;
import com.bnet.data.model.entities.Business;

import java.util.ArrayList;
import java.util.List;

import static com.bnet.data.model.DataProvider.registerProvidable;

public class DataProvider extends ContentProvider {
    private static final Database db = DatabaseFactory.getDatabase();
    public static final String AUTHORITY = "com.bnet.provider";
    private static final ArrayList<Providable> providableList = new ArrayList<>();
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int ACTIVITIES = 0;
    private static final int BUSINESSES = 1;

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

        Providable match = providableList.get(uriMatcher.match(uri));
        Providable item = match.fromContentValues(values);
        id = match.getRepository().addAndReturnAssignedId(item);

        return Uri.withAppendedPath(uri, "" + id);
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
                throw new IllegalArgumentException("No Such Entity");
        }
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

    private Cursor activitiesToCursor() {
        String[] columns = {"_id", "business_id", "country", "description", "price", "start", "end", "type"};

        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (Activity a : db.getAllActivities()) {
            matrixCursor.addRow(new Object[]{
                    a.getId(), a.getBusinessId(), a.getCountry(), a.getDescription(),
                    a.getPrice(), a.getStart(), a.getEnd(), a.getType()});
        }

        return matrixCursor;
    }

    private Cursor businessesToCursor() {
        String[] columns = {"_id", "name", "address", "email", "phone", "link"};

        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (Business b : db.getAllBusinesses()) {
            matrixCursor.addRow(new Object[]{
                    b.getId(), b.getName(), b.getAddress(), b.getEmail(),
                    b.getPhone(), b.getLinkToWebsite()});
        }

        return matrixCursor;
    }
}
