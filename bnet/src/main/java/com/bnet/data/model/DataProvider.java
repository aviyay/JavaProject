package com.bnet.data.model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.bnet.data.model.backend.RepositoriesFactory;
import com.bnet.shared.model.Constants;
import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.backend.ProvidableRepository;
import com.bnet.shared.model.services.utils.CursorUtils;
import com.bnet.shared.model.services.utils.ProvidableUtils;

import java.util.ArrayList;
import java.util.List;

public class DataProvider extends ContentProvider {
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        setupUriMatcher();
        RepositoriesFactory.moveToCloud();
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    private static void setupUriMatcher() {
        for (Class<? extends Providable> p : ProvidableUtils.getAllProvidable())
            addProvidable(p);
    }

    public static void addProvidable(Class<? extends Providable> providable) {
        String basePath = ProvidableUtils.getURIPath(providable);
        int code = ProvidableUtils.getAllProvidable().indexOf(providable);
        addURI(basePath + "/#", code);
        addURI(basePath, code);
    }

    private static void addURI(String path, int code) {
        uriMatcher.addURI(Constants.PROVIDER_AUTHORITY, path, code);
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long id;

        Class<? extends Providable> match = matchProvidable(uri);
        Providable item = ProvidableUtils.contentValuesConvert(match, values);
        id = ProvidableUtils.getRepository(match).addAndReturnAssignedId(item);

        return Uri.withAppendedPath(uri, "" + id);
    }

    private Class<? extends Providable> matchProvidable(Uri uri) {
        if (uriMatcher.match(uri) == -1)
            throw new IllegalArgumentException("No such entity");

        return ProvidableUtils.getAllProvidable().get(uriMatcher.match(uri));
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        ProvidableRepository<Providable> repository;
        List<Providable> chosen;

        repository = ProvidableUtils.getRepository(matchProvidable(uri));

        if (isIdQuery(uri))
            chosen = idQuery(uri, repository);

        else if (isNewsQuery(selection))
            chosen = newsQuery(repository);

        else if (isAllQuery(selection))
            chosen = allQuery(repository);

        else
            chosen = null;

        return CursorUtils.providableListToCursor(chosen);
    }

    private boolean isIdQuery(Uri uri) {
        return uri.getLastPathSegment().matches("^\\d+$");
    }

    private List<Providable> idQuery(Uri uri, ProvidableRepository<Providable> repository) {
        List<Providable> result = new ArrayList<>();

        Providable wanted = repository.getOrNull(ContentUris.parseId(uri));

        if (wanted == null)
            throw new IllegalArgumentException("No such item");

        result.add(wanted);
        return result;
    }

    private boolean isNewsQuery(String selection) {
        return "news".equals(selection);
    }

    private List<Providable> newsQuery(ProvidableRepository<Providable> repository) {
        return repository.getAllNews();
    }

    private boolean isAllQuery(String selection) {
        return selection == null;
    }

    private List<Providable> allQuery(ProvidableRepository<Providable> repository) {
        return repository.getAll();
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
