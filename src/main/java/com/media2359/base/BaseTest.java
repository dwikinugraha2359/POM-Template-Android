package com.media2359.base;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.media2359.helper.HelperAppium;
import com.media2359.helper.HelperFunc;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import bsh.Capabilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;

import org.apache.commons.io.FileUtils;
import java.io.IOException;
import java.net.ServerSocket;

import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class BaseTest {
    public AndroidDriver driver;
    public WebDriverWait wait;
    HelperFunc helperFunc = new HelperFunc();
    HelperAppium helperAppium = new HelperAppium();

    @BeforeClass
    @Parameters({ "deviceName", "platformName", "appPath", "appPackage", "appActivity", "automationName" })
    public void setup(String deviceName, String platformName, String appPath, String appPackage, String appActivity,
            String automationName) throws MalformedURLException {
        // Prepare Appium session
        int port = 4723;
        if (!helperAppium.checkIfServerIsRunnning(port)) {
            System.out.println("Appium Server not running on Port – " + port);
            // Start Appium Server
            helperAppium.startServer(port);
        } else {
            System.out.println("Appium Server already running on Port – " + port);
            // Start Appium Server
            port = 4728;
            // check if port running
            // if running, stop the port
            if (!helperAppium.checkIfServerIsRunnning(port)) {
                System.out.println("Appium Server not running on Port – " + port);
                // Start Appium Server
                helperAppium.startServer(port);
            } else {
                // stop Appium Server
                helperAppium.stopServer();
                // Start Appium Server
                helperAppium.startServer(port);
            }
        }

        initDriver(port, deviceName, platformName, appPath, appPackage, appActivity, automationName);
    }

    @AfterClass
    public void tearDown() {
        // Close session
        driver.quit();
        // stop Appium Server
        helperAppium.stopServer();
    }

    @BeforeMethod
    public void beforeMethodStart() throws IOException {
        // Clean download Directory on test result
        helperFunc.cleanDownloadDir();
    }

    @AfterMethod // AfterMethod annotation - This method executes after every test execution
    public void screenShot(ITestResult result) {
        // using ITestResult.FAILURE is equals to result.getStatus then it enter into if
        // condition
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                // To create reference of TakesScreenshot
                TakesScreenshot screenshot = (TakesScreenshot) driver;
                // Call method to capture screenshotF
                File src = screenshot.getScreenshotAs(OutputType.FILE);
                // Copy files to specific location
                // result.getName() will return name of test case so that screenshot name will
                // be same as test case name
                FileUtils.copyFile(src, new File("screenshoots/" + helperFunc.generateDateNHour() + "/"
                        + result.getName() + "-" + result.getEndMillis() + ".png"));
                System.out.println("Successfully captured a screenshot");
            } catch (Exception e) {
                System.out.println("Exception while taking screenshot " + e.getMessage());
            }
        } else {
            System.out.println(result.getName() + "Test completed successfully");
        }
    }

    private void initDriver(int port, String deviceName, String platformName, String appPath, String appPackage,
            String appActivity, String automationName) throws MalformedURLException {
        System.out.println("Inside initDriver method");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
        // capabilities.setCapability(MobileCapabilityType.APP, appPath);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appPackage);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appActivity);
        capabilities.setCapability("skipUnlock", "true");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, automationName);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, "false");
        capabilities.setCapability("autoGrantPermissions", true);
        // capabilities.setCapability("buildToolsVersion", "28.0");

        // Initialize driver
        driver = new AndroidDriver(new URL("http://0.0.0.0:" + String.valueOf(port) + "/wd/hub"), capabilities);
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }
}