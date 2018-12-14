package com.yourcompany.Tests;

// import Sauce TestNG helper libraries

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.UnexpectedException;

// import testng annotations
// import java libraries

/**
 * Simple TestNG test which demonstrates being instantiated via a DataProvider in order to supply multiple browser combinations.
 *
 * @author Neil Manvar
 */
@Listeners({SauceOnDemandTestListener.class})
public class TestBase {
    // DesiredCapabilities capabilities = new DesiredCapabilities();

    // /*  1. Choose your project */
    // capabilities.setCapability("testobject_api_key", System.getenv("RDC_PRIVATE_KEY"));

    // /*  2. Select your testing device:
    // *   Specify a `platformVersion` without `deviceName` to get any available device with that platform version.
    // *   Not selecting a device at all is also a valid choice, we will select it carefully for you */
    // capabilities.setCapability("platform", "Android");
    // capabilities.setCapability("automationName", "uiautomator2");

    // /*  3. Where is your device located? */
    // // URL EU_endpoint = new URL("https://eu1.appium.testobject.com/wd/hub");
    // URL US_endpoint = new URL("https://us1.appium.testobject.com/wd/hub");

    // /*  The driver will take care of establishing the connection, so we must provide
    // *  it with the correct endpoint and the requested capabilities. */
    // driver = new AndroidDriver(US_endpoint, capabilities);

    /**
     * ThreadLocal variable which contains the  {@link AndroidDriver} instance which is used to perform browser interactions with.
     */
    private ThreadLocal<AndroidDriver> androidDriver = new ThreadLocal<AndroidDriver>();

    /**
     * ThreadLocal variable which contains the Sauce Job Id.
     */
    private ThreadLocal<String> sessionId = new ThreadLocal<String>();

    /**
     * DataProvider that explicitly sets the browser combinations to be used.
     *
     * @param testMethod
     * @return Two dimensional array of objects with browser, version, and platform information
     */
    @DataProvider(name = "hardCodedBrowsers", parallel = true)
    public static Object[][] sauceBrowserDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{"Android", "Android Emulator", "5.0", "1.5.3", "portrait"},
                new Object[]{"Android", "Samsung Galaxy S4 Emulator", "4.4", "1.5.3", "portrait"}
        };
    }

    /**
     * @return the {@link AndroidDriver} for the current thread
     */
    public AndroidDriver getAndroidDriver() {
        return androidDriver.get();
    }

    /**
     * @return the Sauce Job id for the current thread
     */
    public String getSessionId() {
        return sessionId.get();
    }

    /**
     * Constructs a new {@link AndroidDriver} instance which is configured to use the capabilities defined by the browser,
     * version and os parameters, and which is configured to run against ondemand.saucelabs.com, using
     * the username and access key populated by the {@link #authentication} instance.
     *
     * @param platformName      name of the platformName. (Android, iOS, etc.)
     * @param deviceName        name of the device
     * @param platformVersion   Os version of the device
     * @param appiumVersion     appium version
     * @param deviceOrientation device orientation
     * @return
     * @throws MalformedURLException if an error occurs parsing the url
     */
    protected void createDriver(
            String platform,
            String automationName,
            String methodName)
            throws MalformedURLException, UnexpectedException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("testobject_api_key", System.getenv("RDC_PRIVATE_KEY"));
        capabilities.setCapability("platform", platform);
		capabilities.setCapability("automationName", automationName);

        // URL EU_endpoint = new URL("https://eu1.appium.testobject.com/wd/hub");
		URL US_endpoint = new URL("https://us1.appium.testobject.com/wd/hub");

        if (buildTag != null) {
            capabilities.setCapability("build", buildTag);
        }

        // Launch remote browser and set it as the current thread
        androidDriver.set(new AndroidDriver(
                new URL("https://" + authentication.getUsername() + ":" + authentication.getAccessKey() + seleniumURI + "/wd/hub"),
                capabilities));

        String id = ((RemoteWebDriver) getAndroidDriver()).getSessionId().toString();
        sessionId.set(id);
    }

    /**
     * Method that gets invoked after test.
     * Dumps browser log and
     * Closes the browser
     */
    @AfterMethod
    public void tearDown() throws Exception {

        //Gets browser logs if available.
        androidDriver.get().quit();
    }
}
