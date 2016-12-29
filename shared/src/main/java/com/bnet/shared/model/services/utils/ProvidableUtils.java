package com.bnet.shared.model.services.utils;

import android.content.ContentValues;
import android.os.Bundle;

import com.bnet.shared.model.Constants;
import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.backend.ProvidableRepository;
import com.bnet.shared.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.services.converters.bundle.ActivityBundleConverter;
import com.bnet.shared.model.services.converters.contentvalues.ActivityContentValuesConverter;
import com.bnet.shared.model.services.converters.bundle.BundleConverter;
import com.bnet.shared.model.services.converters.bundle.BusinessBundleConverter;
import com.bnet.shared.model.services.converters.contentvalues.BusinessContentValuesConverter;
import com.bnet.shared.model.services.converters.contentvalues.ContentValuesConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProvidableUtils {
    private static class ProvidableRecord<T extends Providable> {
        ProvidableRepository<T> repository;
        String URIPath;
        ContentValuesConverter<T> contentValuesConverter;
        BundleConverter<T> bundleConverter;
    }

    private static Map<Class, ProvidableRecord> records = new HashMap();

    static {
        ProvidableRecord<Activity> activityRecord = new ProvidableRecord<>();
        activityRecord.repository = RepositoriesFactory.getActivitiesRepository();
        activityRecord.URIPath = Constants.ACTIVITIES_URI_PATH;
        activityRecord.contentValuesConverter = new ActivityContentValuesConverter();
        activityRecord.bundleConverter = new ActivityBundleConverter();

        ProvidableRecord<Business> businessRecord = new ProvidableRecord();
        businessRecord.repository = RepositoriesFactory.getBusinessesRepository();
        businessRecord.URIPath = Constants.BUSINESSES_URI_PATH;
        businessRecord.contentValuesConverter = new BusinessContentValuesConverter();
        businessRecord.bundleConverter = new BusinessBundleConverter();

        records.put(Activity.class, activityRecord);
        records.put(Business.class, businessRecord);
    }


    public static ProvidableRepository<Providable> getRepository(Providable providable) {
        return getRepository(providable.getClass());
    }

    public static ProvidableRepository<Providable> getRepository(Class<? extends Providable> type) {
        return records.get(type).repository;
    }


    public static String getURIPath(Providable providable) {
        return getURIPath(providable.getClass());
    }

    public static String getURIPath(Class type) {
        return records.get(type).URIPath;
    }


    public static ContentValues contentValuesConvert(Providable providable) {
        return records.get(providable.getClass()).contentValuesConverter.convert(providable);
    }

    public static Providable contentValuesConvert(Providable providable, ContentValues contentValues) {
        return contentValuesConvert(providable.getClass(), contentValues);
    }

    public static Providable contentValuesConvert(Class type, ContentValues contentValues) {
        return records.get(type).contentValuesConverter.convert(contentValues);
    }

    public static Bundle bundleConvert(Providable providable) {
        return records.get(providable.getClass()).bundleConverter.convert(providable);
    }

    public static Providable bundleConvert(Class type, Bundle bundle) {
        return records.get(type).bundleConverter.convert(bundle);
    }

    public static List<Class<? extends Providable>> getAllProvidable() {
        ArrayList<Class<? extends Providable>> result = new ArrayList<>();

        for (Class<? extends Providable> p : records.keySet())
            result.add(p);

        return result;
    }
}
