package com.media2359.pages;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.media2359.helper.HelperFunc;

import org.apache.commons.io.FileUtils;
import org.aspectj.lang.annotation.Before;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class BasePage {
    public AndroidDriver<AndroidElement> driver;
    public WebDriverWait wait;

    // *********Web Elements*********
    @CacheLookup
    @AndroidFindBy(id = "com.nhg.sandbox:id/tv_action_bar_title")
    public AndroidElement titleScreen;

    // *********Constructor*********
    @BeforeMethod
    public void waitMethod() throws InterruptedException {
        Thread.sleep(500);
    }

    public BasePage(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        wait = new WebDriverWait(driver, 15);
    }

    // *********Page Methods*********
    // Write Text
    public void writeText(AndroidElement elementBy, String text) {
        waitVisibility(elementBy);
        elementBy.sendKeys(text);
    }

    // Read Text
    public String readText(AndroidElement elementBy) {
        waitVisibility(elementBy);
        return elementBy.getText().replace("\n", " ").replace("\r", " ");
    }

    public String readText(MobileElement elementBy) {
        waitVisibility(elementBy);
        return elementBy.getText().replace("\n", " ").replace("\r", " ");
    }

    // Click Method
    public void click(AndroidElement elementBy) {
        waitVisibility(elementBy);
        elementBy.click();
    }

    public void click(MobileElement elementBy) {
        waitVisibility(elementBy);
        elementBy.click();
    }

    // Wait Wrapper Method
    public void waitVisibility(AndroidElement elementBy) {
        wait.until(ExpectedConditions.visibilityOf(elementBy));
    }

    public void waitVisibility(MobileElement elementBy) {
        wait.until(ExpectedConditions.visibilityOf(elementBy));
    }

    // Assert Visibility
    public void assertVisibility(AndroidElement elementBy) {
        waitVisibility(elementBy);
        Assert.assertTrue(isDisplay(elementBy));
    }

    // Assert text
    public void assertEquals(AndroidElement elementBy, String expectedText) {
        waitVisibility(elementBy);
        Assert.assertEquals(readText(elementBy), expectedText);
        Reporter.log(readText(elementBy));
    }

    // Assert empty
    public void assertEmpty(AndroidElement elementBy) {
        waitVisibility(elementBy);
        Reporter.log(readText(elementBy));
        assertTrue(!readText(elementBy).equals(""));
    }

    // Assert true
    public void assertTrue(boolean condition) {
        Assert.assertTrue(condition);
    }

    // Assert true
    public boolean isTextTrueContain(AndroidElement elementBy, String expText) {
        return expText.contains(readText(elementBy));
    }

    // press back button
    public void pressBackButton() {
        driver.navigate().back();
        // get the source
        // driver.getPageSource();
    }

    // back if title is not expected
    public void backWithIteration(String expTitle, int iterationBack) {
        Reporter.log(readText(titleScreen));
        if (!readText(titleScreen).contains(expTitle)) {
            for (int i = 0; i < iterationBack; i++) {
                driver.navigate().back();
            }
        }
    }

    // { Duty Hours : Violations , Comments , Log Detail { Edit Log,Violations ,
    // Comments }}
    // JSONParser parser = new JSONParser();
    // String s = "[{\"text\": \"Duty Hours\",\"id\": \"1\",\"parentid\":
    // \"-1\"},{\"text\": \"Violations\",\"id\": \"2\",\"parentid\":
    // \"1\"},{\"text\": \"Comments\",\"id\": \"3\",\"parentid\": \"1\"}]";

    // public void backOrStay(String expTitle) throws InterruptedException {

    // try {
    // // Object obj = parser.parse(s);
    // // JSONArray array = (JSONArray) obj;
    // // List<JSONObject> listObj = array;
    // // int iterasi = 0;
    // // String text = readText(titleScreen);

    // // for (Object var : array) {
    // // JSONObject obj2 = (JSONObject) var;
    // // Reporter.log(obj2.get("text").toString());
    // // if (obj2.get("text").toString().equals(text) &&
    // obj2.get("parentid").toString().equals("-1")) {
    // // break;
    // // } else if (obj2.get("text").toString().equals(text) &&
    // !obj2.get("parentid").toString().equals("-1")) {

    // // }
    // // }
    // } catch (org.json.simple.parser.ParseException e) {
    // // TODO: handle exception
    // }
    // // do {
    // // Thread.sleep(1000);
    // // String title = readText(titleScreen);
    // // if (!title.equals(expTitle)) {
    // // // Go back
    // // pressBackButton();
    // // } else {
    // // // stop until title screen equal expTitle
    // // break;
    // // }
    // // } while (true);
    // }

    // tap on screen
    // to focus into the app again
    public void tapScreen(int x, int y) {
        TouchAction action = new TouchAction(driver);
        action.tap(PointOption.point(x, y)).perform();
    }

    public void minimizeMaximize() {
        try {
            ((AndroidDriver) driver).runAppInBackground(Duration.ofSeconds(5));
            ((AppiumDriver) driver).activateApp("com.nhg.sandbox");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // IsDisplayed
    public Boolean isDisplay(AndroidElement elementBy) {
        Reporter.log(String.valueOf(elementBy.isDisplayed()));
        return elementBy.isDisplayed();
    }

    public Boolean isDisplay(MobileElement elementBy) {
        Reporter.log(String.valueOf(elementBy.isDisplayed()));
        return elementBy.isDisplayed();
    }

    public Boolean isDisplayNull(AndroidElement elementBy) {
        if (elementBy != null) {
            System.out.println("Element is Present");
            return true;
        } else {
            System.out.println("Element is Absent");
            return false;
        }
    }

    public Boolean isDisplayNull(MobileElement elementBy) {
        if (elementBy != null) {
            System.out.println("Element is Present");
            return true;
        } else {
            System.out.println("Element is Absent");
            return false;
        }
    }

    public Boolean isDisplayList(List<AndroidElement> elementBy) {
        if (elementBy.size() != 0) {
            System.out.println("Element is Present");
            return true;
        } else {
            System.out.println("Element is Absent");
            return false;
        }
    }

    public Boolean isDisplayListME(List<MobileElement> elementBy) {
        if (elementBy.size() != 0) {
            System.out.println("Element is Present");
            return true;
        } else {
            System.out.println("Element is Absent");
            return false;
        }
    }

    // Vertical Swipe by percentages
    public void verticalSwipeByPercentages(double startPercentage, double endPercentage, double anchorPercentage) {
        Dimension size = driver.manage().window().getSize();
        // Reporter.log(String.valueOf(size.width + " : " + size.height));
        int anchor = (int) (size.width * anchorPercentage / 100);
        int startPoint = (int) (size.height * startPercentage / 100);
        int endPoint = (int) (size.height * endPercentage / 100);
        // Reporter.log(String.valueOf(anchor + " : " + startPoint + " : " + endPoint));

        new TouchAction(driver).longPress(PointOption.point(anchor, startPoint))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000)))
                .moveTo(PointOption.point(anchor, endPoint)).release().perform();
    }

    // Vertical Swipe by Point
    public void verticalSwipeByPoint(Point startPoint, Point endPoint) {
        new TouchAction(driver).longPress(PointOption.point(startPoint))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000))).moveTo(PointOption.point(endPoint))
                .release().perform();
    }

    // long press on specific point
    public void longPressAtPoint(Point point) {
        new TouchAction(driver).longPress(PointOption.point(point))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000))).release().perform();
    }

    AndroidElement resultListElement;

    // Get Android Element on List
    public AndroidElement getElementOnList(List<AndroidElement> listElementBy, String expText) {
        for (int i = 0; i < listElementBy.size(); i++) {
            if (isTextTrueContain(listElementBy.get(i), expText)) {
                resultListElement = listElementBy.get(i);
            } else {
                Reporter.log(listElementBy.get(i).getTagName() + "| " + String.valueOf(i) + "| false");
            }
        }
        return resultListElement;
    }

    // Get Android Element on List
    public void assertListElementEmpty(List<AndroidElement> listElementBy) {
        // Report.log(listElementBy.)
        for (AndroidElement var : listElementBy) {
            assertTrue(!readText(var).equals(""));
        }
    }

    // Choose Date Picker
    public void chooseDateOnDP(Date expDate, AndroidElement prevMonthDatePicker2, AndroidElement nextMonthDatePicker2,
            AndroidElement yearTxtDatePicker2, AndroidElement dateTxtDatePicker2)
            throws ParseException, InterruptedException {

        Date dateDP = new Date();
        // change expDate into yearMonth
        YearMonth yearMonthExp = YearMonth.of(expDate.getYear(), expDate.getMonth());

        while (true) {
            Thread.sleep(1000);
            // get date Picker date
            dateDP = HelperFunc.stringDPToDate(readText(dateTxtDatePicker2) + " " + readText(yearTxtDatePicker2));
            YearMonth yearMonthDP = YearMonth.of(dateDP.getYear(), dateDP.getMonth());

            if (yearMonthExp.compareTo(yearMonthDP) > 0) {
                // Date1 is after Date2
                click(nextMonthDatePicker2);
                Thread.sleep(500);
                click(driver.findElementByXPath(
                        "//android.view.View[contains(@content-desc, '" + expDate.getDate() + "')]"));
            } else if (yearMonthExp.compareTo(yearMonthDP) < 0) {
                // Date1 is before Date2
                click(prevMonthDatePicker2);
                Thread.sleep(500);
                click(driver.findElementByXPath(
                        "//android.view.View[contains(@content-desc, '" + expDate.getDate() + "')]"));
            } else {
                // stop until year month date picker equal with expected
                break;
            }
        }

        Reporter.log(HelperFunc.dateToEMMMdd(dateDP));

    }

    // generate File
    public void generateFile(String originalPath, String copiedPath) throws IOException {

        File original = new File(originalPath);
        File copied = new File(copiedPath);
        FileUtils.copyFile(original, copied);
    }

    // generate Random number
    public String generateRandomNumber(int range, String digit) {
        Random rand = new Random();

        // Obtain a number between [0 - 49].
        int n = rand.nextInt(range);

        // Add 1 to the result to get a number from the required range
        // (i.e., [1 - 50]).
        n += 1;
        String formatted = String.format(digit, n);
        return formatted;
    }

    // to generate date
    public String generateDate() {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    // to generate date with hour
    public String generateDateNHour() {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy-HH");
        Date date = new Date();
        return dateFormat.format(date);
    }

    // to generate file with 2 row value
    public void generateFile2Row(String fileName, String rowOne, String rowTwo)
            throws UnsupportedEncodingException, FileNotFoundException {
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        writer.println(rowOne);
        writer.println(rowTwo);
        writer.close();
    }

    // verify file downloaded on directory
    public boolean isFileDownloaded(String downloadPath) {
        File dir = new File(downloadPath);
        File[] dirContents = dir.listFiles();

        // verify the count of file on directory
        if (dirContents.length > 0) {
            return true;
        }

        return false;
    }

}