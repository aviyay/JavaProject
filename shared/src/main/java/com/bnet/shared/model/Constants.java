package com.bnet.shared.model;

public class Constants {
    public static final String PROVIDER_AUTHORITY = "com.bnet.provider";
    public static final String ACTIVITIES_URI_PATH = "activities";
    public static final String BUSINESSES_URI_PATH = "businesses";
    public static final String UPDATE_ACTION = "com.bnet.action.UPDATE";

    public class BusinessContract {
        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String EMAIL = "email";
        public static final String PHONE = "phone";
        public static final String LINK = "link";

        public static final String ADDRESS_COUNTRY = "country";
        public static final String ADDRESS_CITY = "city";
        public static final String ADDRESS_STREET = "street";
    }

    public class ActivityContract {
        public static final String ID = "_id";
        public static final String BUSINESS_ID = "business_id";
        public static final String DESCRIPTION = "description";
        public static final String COUNTRY = "country";
        public static final String PRICE = "price";
        public static final String START = "start";
        public static final String END = "end";
        public static final String TYPE = "type";
    }
}
