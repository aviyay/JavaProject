package com.bnet.shared.model.services.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;

import com.bnet.shared.model.backend.Providable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Utility class for working with cursors
 */
public class CursorUtils {
    /**
     * Merge multiple cursors to one
     * @param cursors The cursors to merge
     * @return The merged cursor
     */
    public static Cursor mergeCursors (List<Cursor> cursors) {
        Cursor[] cursorArray = new Cursor[cursors.size()];
        return new MergeCursor(cursors.toArray(cursorArray)) ;
    }

    /**
     * Convert multiple providables to cursor
     * @param providables The providables to convert
     * @return The result cursor
     */
    static Cursor ProvidableToCursor(Providable... providables) {
        return providableListToCursor(Arrays.asList(providables));
    }

    /**
     * Convert list of providables to cursor
     * @param providableList The list of providables to convert
     * @return The result cursor
     */
    public static Cursor providableListToCursor(List<? extends Providable> providableList) {
        if (providableList == null || providableList.size() == 0)
            return null;

        String[] columns = CursorUtils.getMatrixColumns(providableList.get(0));
        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (Providable item : providableList)
            matrixCursor.addRow(CursorUtils.providableToObjectArray(item));

        return matrixCursor;
    }

    /**
     * Get the columns of the cursor matrix for a given( type of) providable
     * @param providable The providable to take the type from
     * @return The columns of the matrix
     */
    private static String[] getMatrixColumns(Providable providable) {
        ContentValues values = ProvidableUtils.contentValuesConvert(providable);
        String[] columns = new String[values.size()];
        values.keySet().toArray(columns);

        return columns;
    }

    /**
     * Convert providable to array of it's attributes as objects
     * @param providable The providable to convert
     * @return The array of objects
     */
    private static Object[] providableToObjectArray(Providable providable) {
        ContentValues values = ProvidableUtils.contentValuesConvert(providable);
        Object[] result = new Object[values.size()];

        int idx = 0;
        for (Map.Entry<String, Object> pair : values.valueSet())
            result[idx++] = pair.getValue();

        return result;
    }

    /**
     * convert a cursor to list of providables
     * @param type The type of providables that the cursor holds
     * @param cursor The cursor to convert
     * @param <T> The type of providable
     * @return The list of providables that the cursor holds
     */
    public static <T extends Providable> List<T> cursorToProvidableList(Class<T> type, Cursor cursor) {
        List<T> result = new ArrayList<>();

        if (cursor == null)
            return result;

        cursor.moveToFirst();

        T container;
        while (!cursor.isAfterLast()) {
            container = CursorUtils.fromMatrixRow(type, cursor);

            if (container != null)
                result.add(container);

            cursor.moveToNext();
        }

        return result;
    }

    /**
     * Extract a providable from the first row in a cursor
     * @param type The type of the saved providable
     * @param cursor The cursor to extract from
     * @param <T> The type of the saved providable
     * @return The extracted Providable
     */
    private static <T extends Providable> T fromMatrixRow(Class<T> type, Cursor cursor) {
        ContentValues values = new ContentValues();

        for (int i = 0; i < cursor.getColumnCount(); i++)
            values.put(cursor.getColumnName(i), cursor.getString(i));

        return ProvidableUtils.contentValuesConvert(type, values);
    }
}