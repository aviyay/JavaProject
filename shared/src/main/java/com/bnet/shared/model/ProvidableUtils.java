package com.bnet.shared.model;

import android.content.ContentValues;

import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.backend.ProvidableRepository;
import com.bnet.shared.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ProvidableUtils {
    private static class ProvidableRecord {
        ProvidableRepository repository;
        String URIPath;
    }

    private static Map<Class, ProvidableRecord> records = new HashMap();

    static {
        ProvidableRecord activityRecord = new ProvidableRecord();
        activityRecord.repository = RepositoriesFactory.getActivitiesRepository();
        activityRecord.URIPath = Constants.ACTIVITIES_URI_PATH;

        ProvidableRecord businessRecord = new ProvidableRecord();
        businessRecord.repository = RepositoriesFactory.getBusinessesRepository();
        businessRecord.URIPath = Constants.BUSINESSES_URI_PATH;

        records.put(Activity.class, activityRecord);
        records.put(Business.class, businessRecord);
    }

    public static ProvidableRepository getRepository(Providable providable) {
        return getRepository(providable.getClass());
    }

    public static ProvidableRepository getRepository(Class type) {
        return records.get(type).repository;
    }

    public static String getURIPath(Providable providable) {
        return getURIPath(providable.getClass());
    }

    public static String getURIPath(Class type) {
        return records.get(type).URIPath;
    }
}
