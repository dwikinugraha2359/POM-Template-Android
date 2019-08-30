package com.media2359.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

/**
 * AuthenticationPage
 */
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public class AuthenticationPage extends BasePage {
    // *********Variable*********
    // private AndroidDriver<AndroidElement> driver;

    // *********Constructor*********
    public AuthenticationPage(AndroidDriver<AndroidElement> driver) {
        super(driver);
    }

    // *********Web Elements*********
    @CacheLookup
    @AndroidFindBy(id = "com.nhg.sandbox:id/tv_action_bar_title")
    private AndroidElement titleScreenEl;
    // Login
    @CacheLookup
    @AndroidFindBy(id = "com.media2359.juztalent:id/btNext")
    private AndroidElement logoLoginEl;
    @CacheLookup
    @AndroidFindBy(id = "com.nhg.sandbox:id/login_email_email_field")
    private AndroidElement emailFieldLoginEl;
    @CacheLookup
    @AndroidFindBy(id = "com.nhg.sandbox:id/login_email_next_button")
    private AndroidElement nextBtnLoginEl;
    @AndroidFindBy(id = "com.nhg.sandbox:id/login_password_password_field")
    private AndroidElement passFieldLoginEl;
    @AndroidFindBy(id = "com.nhg.sandbox:id/login_password_login_button")
    private AndroidElement loginBtnLoginEl;
    @CacheLookup
    @AndroidFindBy(id = "com.nhg.sandbox:id/login_email_forget_password")
    private AndroidElement forgotPassBtnLoginEl;
    @CacheLookup
    @AndroidFindBy(id = "com.nhg.sandbox:id/login_email_disclaimer")
    private AndroidElement disclaimerBtnLoginEl;

    // *********Page Methods*********
    public AuthenticationPage validateLoginPage() throws InterruptedException {
        click(logoLoginEl);
        click(logoLoginEl);
        Thread.sleep(10000);
        // // Check logo is display
        // assertVisibility(logoLoginEl);
        // // Check logo is displat
        // assertVisibility(emailFieldLoginEl);
        // // Check logo is displat
        // assertVisibility(nextBtnLoginEl);
        // // Check logo is displat
        // assertVisibility(forgotPassBtnLoginEl);
        // // Check logo is displat
        // assertVisibility(disclaimerBtnLoginEl);
        return this;
    }
}