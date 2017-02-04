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

    /**
     * Set up the initial URI Matcher
     */
    private static void setupUriMatcher() {
        for (Class<? extends Providable> p : ProvidableUtils.getAllProvidable())
            addProvidable(p);
    }

    /**
     * Add providable into the Content provider
     * @param providable The providable to add
     */
    public static void addProvidable(Class<? extends Providable> providable) {
        String basePath = ProvidableUtils.getURIPath(providable);
        int code = ProvidableUtils.getAllProvidable().indexOf(providable);
        addURI(basePath + "/#", code);
        addURI(basePath, code);
    }

    /**
     * Add URI to the URI matcher
     * @param path Path for the URI
     * @param code code for the inserted URI
     */
    private static void addURI(String path, int code) {
        uriMatcher.addURI(Constants.PROVIDER_AUTHORITY, path, code);
    }

    /**
     * Insert entity into the content provider
     *
     * @param uri The content:// URI of the insertion request.
     * @param values An array of sets of column_name/value pairs to add to the database.
     *    This must not be {@code null}.
     * @return The number of values that were inserted.
     */
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long id;

        Class<? extends Providable> match = matchProvidable(uri);
        Providable item = ProvidableUtils.contentValuesConvert(match, values);
        id = ProvidableUtils.getRepository(match).addAndReturnAssignedId(item);

        return Uri.withAppendedPath(uri, "" + id);
    }

    /**
     * Get the repository from the URI
     * @param uri The Uri to be matched
     * @return The matching repository
     */
    private Class<? extends Providable> matchProvidable(Uri uri) {
        if (uriMatcher.match(uri) == -1)
            throw new IllegalArgumentException("No such entity");

        return ProvidableUtils.getAllProvidable().get(uriMatcher.match(uri));
    }

    /**
     * Send Query to the content provider
     *
     * @param uri The URI to query. This will be the full URI sent by the client;
     *      if the client is requesting a specific record, the URI will end in a record number
     *      that the implementation should parse and add to a WHERE or HAVING clause, specifying
     *      that _id value.
     * @param projection The list of columns to put into the cursor. If
     *      {@code null} all columns are included.
     * @param selection A selection criteria to apply when filtering rows.
     *      If {@code null} then all rows are included.
     * @param selectionArgs NOT SUPPORTED
     * @param sortOrder NOT SUPPORTED
     * @return a Cursor or {@code null}.
     */
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

    /**
     * Is it ID Query
     * @param uri The query URI
     * @return whether the Query is an ID Query
     */
    private boolean isIdQuery(Uri uri) {
        return uri.getLastPathSegment().matches("^\\d+$");
    }

    /**
     * Perform ID Query
     * @param uri The URI of the query
     * @param repository The repository
     * @return The matching entities in the repository
     */
    private List<Providable> idQuery(Uri uri, ProvidableRepository<Providable> repository) {
        List<Providable> result = new ArrayList<>();

        Providable wanted = repository.getOrNull(ContentUris.parseId(uri));

        if (wanted == null)
            throw new IllegalArgumentException("No such item");

        result.add(wanted);
        return result;
    }

    /**
     * Is it News Query
     * @param selection The query type
     * @return whether the Query is an News Query
     */
    private boolean isNewsQuery(String selection) {
        return "news".equals(selection);
    }

    /**
     * Perform News Query
     * @param repository The repository
     * @return All of the new entities in the repository
     */
    private List<Providable> newsQuery(ProvidableRepository<Providable> repository) {
        return repository.getAllNews();
    }

    /**
     * Is it All Query
     * @param selection The query type
     * @return whether the Query is an All Query
     */
    private boolean isAllQuery(String selection) {
        return selection == null;
    }

    /**
     * Perform All Query
     * @param repository The repository
     * @return All of entities in the repository
     */
    private List<Providable> allQuery(ProvidableRepository<Providable> repository) {
        return repository.getAll();
    }

    /**
     * Not yet implemented!
     * @throws UnsupportedOperationException
     */
    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    /**
     * Not yet implemented!
     * @throws UnsupportedOperationException
     */
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    /**
     * Not yet implemented!
     * @throws UnsupportedOperationException
     */
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
