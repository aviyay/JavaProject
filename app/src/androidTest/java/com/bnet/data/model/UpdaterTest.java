package com.bnet.data.model;

import android.icu.util.TimeUnit;
import android.service.chooser.ChooserTargetService;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UpdaterTest {
    Updater updater;

    @Before
    public void setUp() throws Exception {
        updater = new Updater();

    }

    //@Test //need to improve this test to use context
    public void runServiceAndWaitForIntent() throws Exception {
        updater.onHandleIntent(null);
        //Thread.sleep(50000);

    }
}