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

    /**
     * Initialize the business providable record in the providables records
     */
    private static void initializeBusinessRecord() {
        ProvidableRecord<Business> businessRecord = new ProvidableRecord<>();
        businessRecord.repository = RepositoriesFactory.getBusinessesRepository();
        businessRecord.URIPath = Constants.BUSINESSES_URI_PATH;
        businessRecord.contentValuesConverter = new BusinessContentValuesConverter();
        businessRecord.bundleConverter = new BusinessBundleConverter();

        records.put(Business.class, businessRecord);
    }

    /**
     * Initialize the activity providable record in the providables records
     */
    private static void initializeActivityRecord() {
        ProvidableRecord<Activity> activityRecord = new ProvidableRecord<>();
        activityRecord.repository = RepositoriesFactory.getActivitiesRepository();
        activityRecord.URIPath = Constants.ACTIVITIES_URI_PATH;
        activityRecord.contentValuesConverter = new ActivityContentValuesConverter();
        activityRecord.bundleConverter = new ActivityBundleConverter();

        records.put(Activity.class, activityRecord);
    }

    /**
     * Refresh repositories
     */
    public static void refreshRepositories() {
        ((ProvidableRecord<Business>) records.get(Business.class)).repository =
                RepositoriesFactory.getBusinessesRepository();

        ((ProvidableRecord<Activity>) records.get(Activity.class)).repository =
                RepositoriesFactory.getActivitiesRepository();
    }

    /**
     * Get Repository
     * @param providable An entity of the type of the entities in the wanted repository
     * @return The wanted repository
     */
    public static ProvidableRepository<Providable> getRepository(Providable providable) {
        return getRepository(providable.getClass());
    }
    /**
     * Get Repository
     * @param type The type of the entities in the wanted repository
     * @return The wanted repository
     */
    public static ProvidableRepository<Providable> getRepository(Class<? extends Providable> type) {
        return (ProvidableRepository<Providable>) records.get(type).repository;
    }

    /**
     * Get the URI path for the type of the entity
     * @param providable The entity
     * @return The URI path
     */
    public static String getURIPath(Providable providable) {
        return getURIPath(providable.getClass());
    }
    /**
     * Get the URI path for the type
     * @param type The type
     * @return The URI path
     */
    public static String getURIPath(Class<? extends Providable> type) {
        return records.get(type).URIPath;
    }

    /**
     * T to Content Values Converter ({@link Providable})
     * @param providable The providable
     * @param <T> The type of the entity
     * @return The Content Values that represent the entityy
     */
    public static <T extends Providable> ContentValues contentValuesConvert(T providable) {
        Converter<ContentValues, T> converter = (Converter<ContentValues, T>) records.get(providable.getClass()).contentValuesConverter;
        return converter.convert(providable);
    }

    /**
     * Content Values to T Converter
     * @param type The type of the entity to be converted to
     * @param contentValues The content values to be converted
     * @param <T> The type of the entity to be converted to
     * @return The converted entity
     */
    public static <T extends Providable> T contentValuesConvert(Class<T> type, ContentValues contentValues) {
        return (T) records.get(type).contentValuesConverter.convertBack(contentValues);
    }

    /**
     * T to Bundle Converter ({@link Providable})
     * @param providable The providable
     * @param <T> The type of the entity
     * @return The Bundle that represent the entityy
     */
    public static <T extends Providable> Bundle bundleConvert(T providable) {
        Converter<Bundle, T> converter = (Converter<Bundle, T>) records.get(providable.getClass()).bundleConverter;
        return converter.convert(providable);
    }
    /**
     * Bundle to T Converter
     * @param type The type of the entity to be converted to
     * @param bundle The Bundle to be converted
     * @param <T> The type of the entity to be converted to
     * @return The converted entity
     */
    public static <T extends Providable> T bundleConvert(Class<T> type, Bundle bundle) {
        return (T) records.get(type).bundleConverter.convertBack(bundle);
    }

    /**
     * Get a list of all the classes of known providables in the system (currently it's just business and activity)
     * @return The list of all the classes of known providables in the system
     */
    public static List<Class<? extends Providable>> getAllProvidable() {
        ArrayList<Class<? extends Providable>> result = new ArrayList<>();

        for (Class<? extends Providable> p : records.keySet())
            result.add(p);

        return result;
    }
}
