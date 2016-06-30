package com.testauto.seleniumsetup;

import static com.testauto.util.Util.*;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;

public class WebDriverDelegate implements WebDriver {
	
	private WebDriver webDriver;
	
	public WebDriverDelegate(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public void close() {
		webDriver.close();
	}

	public WebElement findElement(By by) {
		return webDriver.findElement(by);
	}

	public WebElement findElementAndWait(By by) {
		waitForElementIsDisplayed(webDriver, by);
		return webDriver.findElement(by);
	}
	
	public List<WebElement> findElements(By by) {
		return webDriver.findElements(by);
	}
	
	public List<WebElement> findElementsAndWait(By by) {
		waitForElementIsDisplayed(webDriver, by);
		return webDriver.findElements(by);
	}
	
//	public Timeouts pageLoadTimeout() {
//		resetImplicitWait(webDriver);
//		return webDriver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
//	}

	public void get(String arg0) {
		webDriver.get(arg0);
	}

	public String getCurrentUrl() {
		return webDriver.getCurrentUrl();
	}

	public String getPageSource() {
		return webDriver.getPageSource();
	}

	public String getTitle() {
		return webDriver.getTitle();
	}

	public String getWindowHandle() {
		return webDriver.getWindowHandle();
	}

	public Set<String> getWindowHandles() {
		return webDriver.getWindowHandles();
	}

	public Options manage() {
		return webDriver.manage();
	}

	public Navigation navigate() {
		return webDriver.navigate();
	}

	public void quit() {
		webDriver.quit();
	}

	public TargetLocator switchTo() {
		return webDriver.switchTo();
	}

	public Object executeScript(String script, Object... args) {
		return ((JavascriptExecutor)webDriver).executeScript(script, args);
	}
	
	public byte[] getScreenshot() {
		WebDriver augmentedDriver = new Augmenter().augment(webDriver);
		return ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.BYTES);
	}
}
