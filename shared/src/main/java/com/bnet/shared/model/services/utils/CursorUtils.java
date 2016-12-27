package com.bnet.shared.model.services.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;

import com.bnet.shared.model.backend.Providable;

import java.util.ArrayList;
import java.util.List;

public class CursorUtils {
    public static <T extends Providable> T fromMatrixRow(final T match, Cursor cursor) {
        ContentValues values = new ContentValues();

        for (int i = 0; i < cursor.getColumnCount(); i++)
            values.put(cursor.getColumnName(i), cursor.getString(i));

        return (T) ProvidableUtils.contentValuesConvert(match.getClass(), values);
    }

    public static String[] getMatrixColumns(Providable providable) {
        ContentValues values = ProvidableUtils.contentValuesConvert(providable);
        String[] columns = new String[values.size()];
        values.keySet().toArray(columns);
        return columns;
    }

    public static Object[] providableToObjectArray(Providable providable) {
        String[] columns = CursorUtils.getMatrixColumns(providable);
        ContentValues values = ProvidableUtils.contentValuesConvert(providable);
        Object[] result = new Object[columns.length];

        for (int i = 0; i < result.length; i++)
            result[i] = values.get(columns[i]);

        return result;
    }

    public static Cursor providableListToCursor(List<Providable> providableList) {
        if (providableList == null || providableList.size() == 0)
            return null;

        String[] columns = CursorUtils.getMatrixColumns(providableList.get(0));
        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (Providable item : providableList)
            matrixCursor.addRow(CursorUtils.providableToObjectArray(item));

        return matrixCursor;
    }

    public static <T extends Providable> List<T> cursorToProvidableList(T match, Cursor cursor) {
        List<T> result = new ArrayList<>();

        if (cursor == null)
            return result;

        T container;

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            container = CursorUtils.fromMatrixRow(match, cursor);

            if (container != null)
                result.add(container);

            cursor.moveToNext();
        }
        return result;
    }

    public static Cursor mergeCursors (List<Cursor> cursors) {
        Cursor[] cursorArray = new Cursor[cursors.size()];
        return new MergeCursor(cursors.toArray(cursorArray)) ;
    }
}