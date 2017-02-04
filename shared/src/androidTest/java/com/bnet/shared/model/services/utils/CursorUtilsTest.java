package com.bnet.shared.model.services.utils;

import android.database.Cursor;

import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.EntitiesSamples;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CursorUtilsTest {
    private List<Activity> activityList;

    @Before
    public void setUp() throws Exception {
        activityList = new ArrayList<>();
        activityList.add(EntitiesSamples.makeActivity());
        activityList.add(EntitiesSamples.makeActivity2());
    }

    @Test
    public void mergeCursors() throws Exception {
        List<Cursor> cursorList = new ArrayList<>();
        for (Activity activity : activityList)
            cursorList.add(CursorUtils.ProvidableToCursor(activity));

        Cursor merged = CursorUtils.mergeCursors(cursorList);
        List<Activity> result = CursorUtils.cursorToProvidableList(Activity.class, merged);

        assertEquals(activityList, result);
    }

    @Test
    public void providableListToCursorAndBack() throws Exception {
        Cursor cursor = CursorUtils.providableListToCursor(activityList);
        List<Activity> result = CursorUtils.cursorToProvidableList(Activity.class, cursor);

        assertEquals(activityList, result);
    }

}