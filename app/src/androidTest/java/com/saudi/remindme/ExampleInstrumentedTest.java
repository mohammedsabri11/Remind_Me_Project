package com.saudi.remindme;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.saudi.remindme", appContext.getPackageName());
    }
    @Test
    private void isPasswordStrong() {
        String value = "23442433@As";
        //"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&(){}:;',?/*~$^+=<>]).{8,20}$"
        String pa ="^(?=.*[a-zA-Z]{8,})(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]{8,}$";
        if (!value.matches(pa)) {
         System.out.println(false);

        }else
        System.out.println(true);

    }
}