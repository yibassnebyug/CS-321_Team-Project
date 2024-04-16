package com.example.cs_321_team_project;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTests {

    public Context appContext;
    /**
     * Get app context before each test
     */
    @Before
    public void useAppContext() {
        // gets the instrumentation before each test
        // not clear if @Before is better that @BeforeClass for this test set up
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }
    @Test public void test_AppContext() {
        // if we can get the package name, then the context has been initialized
        assertEquals("com.example.cs_321_team_project", appContext.getPackageName());
    }
    @Test
    public void test_launch() {
        // if is root then launch was successful
        onView(isRoot());
    }

}