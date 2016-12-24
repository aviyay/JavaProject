package com.bnet.data.model;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.bnet.shared.model.Constants;
import com.bnet.shared.model.CursorUtils;
import com.bnet.shared.model.ProvidableUtils;
import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;

import java.util.ArrayList;

public class DataProvider extends ContentProvider {
    private static final ArrayList<Providable> providableList = new ArrayList<>();
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static void registerProvidable(Providable providable) {
        providableList.add(providable);
        uriMatcher.addURI(Constants.PROVIDER_AUTHORITY, ProvidableUtils.getURIPath(providable), providableList.indexOf(providable));
    }

    @Override
    public boolean onCreate() {
        if (providableList.size() == 0) {
            for (Providable p : ProvidableUtils.getAllProvidable())
                registerProvidable(p);
        }

        return true;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        int id;

        Providable match = matchProvidable(uri);
        Providable item = match.fromContentValues(values);
        id = ProvidableUtils.getRepository(match).addAndReturnAssignedId(item);

        return Uri.withAppendedPath(uri, "" + id);
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Providable match = matchProvidable(uri);

        String[] columns = CursorUtils.getMatrixColumns(match);
        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (Object item : ProvidableUtils.getRepository(match).getAll()) {
            matrixCursor.addRow(CursorUtils.ProvidableToObjectArray((Providable) item));
        }

        return matrixCursor;
    }

    private Providable matchProvidable(Uri uri) {
        if (uriMatcher.match(uri) >= providableList.size())
            throw new IllegalArgumentException("No Such Entity");

        return providableList.get(uriMatcher.match(uri));
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
