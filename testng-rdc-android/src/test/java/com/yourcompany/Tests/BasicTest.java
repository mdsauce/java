package com.yourcompany.Tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.TouchAction;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import java.net.URL;
import org.testng.annotations.Test;

public class BasicTest {

	private AppiumDriver driver;

	@BeforeMethod
	public void setUp() throws Exception {

		@DataProvider(name = "hardCodedBrowsers", parallel = true)
    	public static Object[][] sauceBrowserDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{"Android", "Android Emulator", "5.0", "1.5.3", "portrait"},
                new Object[]{"Android", "Samsung Galaxy S4 Emulator", "4.4", "1.5.3", "portrait"}
        	};
		}

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
	}

	@Test
	public void testMethod() {
		try {
			Thread.sleep(10000);
		}catch (InterruptedException e){
			e.printStackTrace();
		}

		WebDriverWait wait = new WebDriverWait(driver,180);
		WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("yinz_menu_activity_titlebar"))));
		nextButton.click();
		nextButton.click();
		nextButton.click();
		nextButton.click();
		//wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//android.widget.Button[@text='ALLOW']"))));
	}

	/* We disable the driver after EACH test has been executed. */
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}