package com.yourcompany.Tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.List;

public class Base {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        /*  1. Choose your project */
        capabilities.setCapability("testobject_api_key", System.getenv("CALCULATOR_API_KEY"));

        /*  2. Select your testing device:
         *   Specify a `platformVersion` without `deviceName` to get any available device with that platform version.
         *   Not selecting a device at all is also a valid choice, we will select it carefully for you */
        capabilities.setCapability("platform", "android");
        capabilities.setCapability("deviceName", "Huawei_Nexus_6p_8_real_us"); // Optional

        /*  3. Where is your device located? */
        URL EU_endpoint = new URL("https://eu1.appium.testobject.com/wd/hub");
        URL US_endpoint = new URL("https://us1.appium.testobject.com/wd/hub");

        /*  The driver will take care of establishing the connection, so we must provide
         *  it with the correct endpoint and the requested capabilities. */
        driver = new AndroidDriver(US_endpoint, capabilities);
    }

    /* We disable the driver after EACH test has been executed. */
    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testMethod() {
        List<LogEntry> logEntries = driver.manage().logs().get("logcat").getAll();
        String denial = logEntries.get(0).getMessage();
        if (denial.contains("Permission denied")) {
            System.out.println("test failed :(" + "\n" + denial);
        } else {
            System.out.println("test passed. No permission denied.");
        }
    }
}