package com.bnet.data.model;

import org.junit.Before;

public class UpdaterTest {
    private Updater updater;

    @Before
    public void setUp() throws Exception {
        updater = new Updater();
    }

    //@Test //it's not really a test
    public void runServiceAndWaitForIntent() throws Exception {
        updater.onHandleIntent(null);
        //Thread.sleep(50000);

    }
}