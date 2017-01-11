package com.bnet.shared.model.services.utils;

import android.content.ContentValues;
import android.os.Bundle;

import com.bnet.shared.model.Constants;
import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.backend.ProvidableRepository;
import com.bnet.shared.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.services.converters.Converter;
import com.bnet.shared.model.services.converters.bundle.*;
import com.bnet.shared.model.services.converters.contentvalues.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProvidableUtils {
    private static class ProvidableRecord<T extends Providable> {
        ProvidableRepository<T> repository;
        String URIPath;
        Converter<ContentValues, T> contentValuesConverter;
        Converter<Bundle, T> bundleConverter;
    }

    private static final Map<Class<? extends Providable>, ProvidableRecord<? extends Providable>> records = new HashMap<>();

    static {
        initializeBusinessRecord();
        initializeActivityRecord();
    }

    private static void initializeBusinessRecord() {
        ProvidableRecord<Business> businessRecord = new ProvidableRecord<>();
        businessRecord.repository = RepositoriesFactory.getBusinessesRepository();
        businessRecord.URIPath = Constants.BUSINESSES_URI_PATH;
        businessRecord.contentValuesConverter = new BusinessContentValuesConverter();
        businessRecord.bundleConverter = new BusinessBundleConverter();

        records.put(Business.class, businessRecord);
    }

    private static void initializeActivityRecord() {
        ProvidableRecord<Activity> activityRecord = new ProvidableRecord<>();
        activityRecord.repository = RepositoriesFactory.getActivitiesRepository();
        activityRecord.URIPath = Constants.ACTIVITIES_URI_PATH;
        activityRecord.contentValuesConverter = new ActivityContentValuesConverter();
        activityRecord.bundleConverter = new ActivityBundleConverter();

        records.put(Activity.class, activityRecord);
    }


    public static void refreshRepositories() {
        ((ProvidableRecord<Business>) records.get(Business.class)).repository =
                RepositoriesFactory.getBusinessesRepository();

        ((ProvidableRecord<Activity>) records.get(Activity.class)).repository =
                RepositoriesFactory.getActivitiesRepository();
    }

    public static ProvidableRepository<Providable> getRepository(Providable providable) {
        return getRepository(providable.getClass());
    }

    public static ProvidableRepository<Providable> getRepository(Class<? extends Providable> type) {
        return (ProvidableRepository<Providable>) records.get(type).repository;
    }


    public static String getURIPath(Providable providable) {
        return getURIPath(providable.getClass());
    }

    public static String getURIPath(Class<? extends Providable> type) {
        return records.get(type).URIPath;
    }


    public static <T extends Providable> ContentValues contentValuesConvert(T providable) {
        Converter<ContentValues, T> converter = (Converter<ContentValues, T>) records.get(providable.getClass()).contentValuesConverter;
        return converter.convert(providable);
    }

    public static <T extends Providable> T contentValuesConvert(Class<T> type, ContentValues contentValues) {
        return (T) records.get(type).contentValuesConverter.convertBack(contentValues);
    }

    public static <T extends Providable> Bundle bundleConvert(T providable) {
        Converter<Bundle, T> converter = (Converter<Bundle, T>) records.get(providable.getClass()).bundleConverter;
        return converter.convert(providable);
    }

    public static <T extends Providable> T bundleConvert(Class<T> type, Bundle bundle) {
        return (T) records.get(type).bundleConverter.convertBack(bundle);
    }

    public static List<Class<? extends Providable>> getAllProvidable() {
        ArrayList<Class<? extends Providable>> result = new ArrayList<>();

        for (Class<? extends Providable> p : records.keySet())
            result.add(p);

        return result;
    }
}
