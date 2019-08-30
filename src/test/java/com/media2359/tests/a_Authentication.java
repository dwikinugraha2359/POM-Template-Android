package com.media2359.tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import com.media2359.base.BaseTest;
import com.media2359.pages.AuthenticationPage;

/**
 * a_Authentication
 */
public class a_Authentication extends BaseTest {

    /*
     * Feature : Navigated to contact us page and assert that display contact
     * person, payment instruction, payment info Given : User already logged in to
     * Goldbell Portal as Admin When : User click on "Contact Us" menu Then : User
     * will be able to see "Payment" contact person information, "Customer Service"
     * contact person information, payment information, and payment instruction.
     */
    @Test(priority = 0)
    public void A_001_LaunchApp() {

        // *************PAGE INSTANTIATIONS*************
        AuthenticationPage authenticationPage = new AuthenticationPage(driver);

        // *************PAGE METHODS********************
        try {
            authenticationPage.validateLoginPage();
            System.out.println("Test completed successfully");
        } catch (Exception e) {
            // TODO: handle exception
            Assert.fail(e.getMessage());
        }
    }
}