package com.students.aviyay_and_shy.bnet.model.backend;

import com.students.aviyay_and_shy.bnet.model.datasource.ListsDatabase;

public class DatabaseFactory {

    private static Database database = new ListsDatabase();

    public static Database getDatabase() {
        return database;
    }
}
