package com.bnet.shared.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.bnet.shared.model.backend.Providable;

public class CursorUtils {
    public static <T extends Providable> T fromMatrixRow(T match, Cursor cursor) throws Exception {
        ContentValues values = new ContentValues();

        for (int i = 0; i < cursor.getColumnCount(); i++)
            values.put(cursor.getColumnName(i), cursor.getString(i));

        return (T) match.fromContentValues(values);
    }

    public static String[] getMatrixColumns(Providable providable) {
        ContentValues values = providable.toContentValues(providable);
        String [] columns = new String[values.size()];
        values.keySet().toArray(columns);
        return columns;
    }

    public static Object[] ProvidableToObjectArray(Providable providable) {
        String[] columns = CursorUtils.getMatrixColumns(providable);
        ContentValues values = providable.toContentValues(providable);
        Object[] result = new Object[columns.length];

        for (int i = 0; i < result.length; i++)
            result[i] = values.get(columns[i]);

        return result;
    }
}
