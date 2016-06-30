package com.testauto.util;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Util {
	
	public static final int TIMEOUT = 10;
	
	public static WebElement waitForElementIsDisplayed(WebDriver driver, final By by,
			int timeOutInSeconds) {
		WebElement element = null;
		try {
			driver.manage().timeouts().implicitlyWait(0, SECONDS);

			element = new WebDriverWait(driver, timeOutInSeconds).ignoring(
					NoSuchElementException.class).until(
					ExpectedConditions.visibilityOfElementLocated(by));

			driver.manage().timeouts().implicitlyWait(10, SECONDS);
		} catch (Exception e) {
			Assert.fail(String.format("Timeout when waiting %d seconds for '%s' ",
					timeOutInSeconds, by));
		}
		return element;
	}
	
	public static WebElement waitForElementIsDisplayed2(WebDriver driver, final By by, int timeOutInSeconds)
			throws NoSuchElementException,TimeoutException, NullPointerException {
		WebElement element = null;

		driver.manage().timeouts().implicitlyWait(0, SECONDS);

		element = new WebDriverWait(driver, timeOutInSeconds).ignoring(NoSuchElementException.class)
				.ignoring(TimeoutException.class).ignoring(NullPointerException.class)
				.until(ExpectedConditions.visibilityOfElementLocated(by));

		driver.manage().timeouts().implicitlyWait(10, SECONDS);
		return element;
	}

	public static WebElement waitForElementIsDisplayed(WebDriver driver, final By by) {
		return waitForElementIsDisplayed(driver, by, TIMEOUT);
	}
	
	public static boolean isElementPresent(WebDriver driver, By by) {
		try {
			return waitForElementIsDisplayed2(driver, by,10).isDisplayed();
		} catch (NullPointerException | NoSuchElementException | TimeoutException e) {
			return false;
		}
	}
	
	public static void sleep(long timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}


