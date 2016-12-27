package com.bnet.data.model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.bnet.shared.model.Constants;
import com.bnet.shared.model.backend.ProvidableRepository;
import com.bnet.shared.model.services.utils.CursorUtils;
import com.bnet.shared.model.services.utils.ProvidableUtils;
import com.bnet.shared.model.backend.Providable;

import java.util.ArrayList;
import java.util.List;

public class DataProvider extends ContentProvider {
    private static final ArrayList<Providable> providableList = new ArrayList<>();
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static void registerProvidable(Providable providable) {
        providableList.add(providable);
        String basePath = ProvidableUtils.getURIPath(providable);

        uriMatcher.addURI(Constants.PROVIDER_AUTHORITY,
                basePath + "/#", providableList.indexOf(providable));

        uriMatcher.addURI(Constants.PROVIDER_AUTHORITY,
                basePath, providableList.indexOf(providable));
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
        Providable item = ProvidableUtils.contentValuesConvert(match.getClass(), values);
        id = ProvidableUtils.getRepository(match).addAndReturnAssignedId(item);

        return Uri.withAppendedPath(uri, "" + id);
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Providable match = matchProvidable(uri);
        ProvidableRepository repository = ProvidableUtils.getRepository(match);

        List<Providable> chosen;

        int wanted_id = getIdOrMinusOne(uri);

        if (wanted_id != -1)
            selection = "id";

        if (selection == null)
            selection = "";

        switch (selection) {
            case "id":
                chosen = new ArrayList<>();
                chosen.add(findItem(repository, wanted_id));
                break;
            case "news":
                chosen = repository.getAllNews();
                break;
            default:
                chosen = repository.getAll();
                break;
        }

        return CursorUtils.providableListToCursor(chosen);
    }

    private Providable matchProvidable(Uri uri) {
        if (uriMatcher.match(uri) == -1)
            throw new IllegalArgumentException("No such entity");

        return providableList.get(uriMatcher.match(uri));
    }

    private int getIdOrMinusOne(@NonNull Uri uri) {
        if (uri.getLastPathSegment().matches("^\\d+$"))
            return (int) ContentUris.parseId(uri);
        return -1;
    }

    private Providable findItem(ProvidableRepository<Providable> repository, int wanted_id) {
        for (Providable p : repository.getAll())
            if (p.getId() == wanted_id)
                return p;

        throw new IllegalArgumentException("Invalid item id");
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
