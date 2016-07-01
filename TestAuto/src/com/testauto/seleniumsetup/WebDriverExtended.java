package com.testauto.seleniumsetup;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class WebDriverExtended extends WebDriverDelegate {

	private String baseUrl;

	public WebDriverExtended(WebDriver webDriver, String baseUrl) {
		super(webDriver);
		this.baseUrl = baseUrl;
	}
	
	public WebDriverExtended(WebDriver webDriver) {
		super(webDriver);
	}

	public boolean isElementPresent(By by) {
		try {
			findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public void gotoUrl(String url) {
//		gotoUrl(url, false);
		get(url);
	}
	
	public void gotoUrl(String url, boolean absolute) {
		String hostPlusPort = absolute ? "" : baseUrl;
		get(hostPlusPort + url);
	}
}
