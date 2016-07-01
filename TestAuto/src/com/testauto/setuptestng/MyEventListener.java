package com.testauto.setuptestng;

import static com.testauto.setuptestng.SeleniumTestCaseBase.LOG;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;


public class MyEventListener implements WebDriverEventListener {	
	
	private String getClassName() {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		for (StackTraceElement stackTraceElement : stackTraceElements) {
			if (stackTraceElement.getClassName().contains("objects")){
				return stackTraceElement.getClassName();
			}else if (stackTraceElement.getClassName().contains("actions")) {
				return stackTraceElement.getClassName();
			}else if(stackTraceElement.getClassName().equals(SeleniumTestCaseBase.class.getName())){
				return stackTraceElement.getClassName();
			}
		}
		return "Other";		
	}
	
	
	
	@Override
	public void afterChangeValueOf(WebElement arg0, WebDriver arg1) {
		LOG.info(getClassName() +":   Wrote/cleared element: '" + arg0.toString().split("->")[1].replaceAll("]", "") + "'");

	}

	@Override
	public void afterClickOn(WebElement arg0, WebDriver arg1) {
		LOG.info(getClassName() +":   Clicked on element using locator '" + arg0.toString().split("->")[1].replaceAll("]", "") + "'");
	}

	@Override
	public void afterFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		LOG.info(getClassName() +":   Found element using locator '" + arg0.toString() + "'");

	}

	@Override
	public void afterNavigateBack(WebDriver arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterNavigateForward(WebDriver arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterNavigateTo(String arg0, WebDriver arg1) {
		LOG.info(getClassName() +":   Opened address:'" + arg0 + "'");
	}

	@Override
	public void afterScript(String arg0, WebDriver arg1) {
		LOG.info(getClassName() +":   Executed script:'" + arg0 + "'");
	}

	@Override
	public void beforeChangeValueOf(WebElement arg0, WebDriver arg1) {
		LOG.info(getClassName() +":   Writing/clearing element: '" + arg0.toString().split("->")[1].replaceAll("]", "") + "'");

	}

	@Override
	public void beforeFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		LOG.info(getClassName() +":   Trying to find element using locator '" + arg0.toString() + "'");

	}

	@Override
	public void beforeNavigateBack(WebDriver arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeNavigateForward(WebDriver arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeNavigateTo(String arg0, WebDriver arg1) {
		LOG.info(getClassName() +":   Opening address:'" + arg0 + "'");

	}

	@Override
	public void beforeScript(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onException(Throwable arg0, WebDriver arg1) {
		//log.error("Encountered error: ", arg0);
	}

	@Override
	public void beforeClickOn(WebElement element, WebDriver driver) {
		LOG.info(getClassName() +":   Clicked on element using locator '" + element.toString().split("->")[1].replaceAll("]", "") + "'");		
	}



	@Override
	public void afterNavigateRefresh(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void beforeNavigateRefresh(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}

}