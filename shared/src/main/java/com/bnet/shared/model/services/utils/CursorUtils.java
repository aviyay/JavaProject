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

public class CursorUtils {
    public static Cursor mergeCursors (List<Cursor> cursors) {
        Cursor[] cursorArray = new Cursor[cursors.size()];
        return new MergeCursor(cursors.toArray(cursorArray)) ;
    }

    static Cursor ProvidableToCursor(Providable... providables) {
        return providableListToCursor(Arrays.asList(providables));
    }
    public static Cursor providableListToCursor(List<? extends Providable> providableList) {
        if (providableList == null || providableList.size() == 0)
            return null;

        String[] columns = CursorUtils.getMatrixColumns(providableList.get(0));
        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (Providable item : providableList)
            matrixCursor.addRow(CursorUtils.providableToObjectArray(item));

        return matrixCursor;
    }

    private static String[] getMatrixColumns(Providable providable) {
        ContentValues values = ProvidableUtils.contentValuesConvert(providable);
        String[] columns = new String[values.size()];
        values.keySet().toArray(columns);

        return columns;
    }

    private static Object[] providableToObjectArray(Providable providable) {
        ContentValues values = ProvidableUtils.contentValuesConvert(providable);
        Object[] result = new Object[values.size()];

        int idx = 0;
        for (Map.Entry<String, Object> pair : values.valueSet())
            result[idx++] = pair.getValue();

        return result;
    }

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

    private static <T extends Providable> T fromMatrixRow(Class<T> type, Cursor cursor) {
        ContentValues values = new ContentValues();

        for (int i = 0; i < cursor.getColumnCount(); i++)
            values.put(cursor.getColumnName(i), cursor.getString(i));

        return ProvidableUtils.contentValuesConvert(type, values);
    }
}