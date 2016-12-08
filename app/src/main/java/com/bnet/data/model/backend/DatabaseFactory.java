package com.bnet.data.model.backend;

import com.bnet.data.model.datasource.ListsDatabase;

public class DatabaseFactory {

    private static Database database = new ListsDatabase();

    public static Database getDatabase() {
        return database;
    }
}
