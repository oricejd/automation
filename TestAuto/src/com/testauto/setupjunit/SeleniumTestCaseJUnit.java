package com.testauto.setupjunit;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openqa.selenium.Platform.ANY;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;


import com.testauto.seleniumsetup.WebDriverExtended;


public class SeleniumTestCaseJUnit {

	public static Logger LOG = Logger.getLogger("testLogger");
	public static Logger LOG_TIME = Logger.getLogger("timeLogger");

	private String baseUrl;
	private long startTime;
	private WebDriverExtended webDriver;
	private String childClassName = this.getClass().getName();

	@Rule
	public TestRule testWatcher = new TestWatcher() {

		@Override
		protected void starting(Description description) {
			startTime = System.currentTimeMillis();
			String grid = System.getProperty("grid");

			FirefoxProfile ffProfile = new FirefoxProfile();
			WebDriver baseWebDriver = null;
			try {
				DesiredCapabilities dc = DesiredCapabilities.firefox();
				dc.setCapability("platform", ANY);
				ffProfile.setPreference("plugin.state.flash", 0);

				if ("yes".equalsIgnoreCase(grid)) {
					dc.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
					baseWebDriver = new EventFiringWebDriver(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc)).register(new MyEventListenerJUnit());
				} else {
					baseWebDriver = new EventFiringWebDriver(new FirefoxDriver(ffProfile)).register(new MyEventListenerJUnit());
				}

				baseWebDriver.manage().window().maximize();
				((JavascriptExecutor) baseWebDriver).executeScript("window.focus();");
				baseUrl = System.getProperty("baseUrl");

				webDriver = new WebDriverExtended(baseWebDriver, baseUrl);
				webDriver.manage().timeouts().implicitlyWait(10, SECONDS);
				webDriver.manage().timeouts().pageLoadTimeout(40, SECONDS);

			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		};

		@Override
		protected void finished(Description description) {
			try {
				webDriver.quit();
			} catch (Exception e) {
				System.out.println("Missing browser instance!");
			}
			LOG_TIME.info(childClassName + " took executing : " + (System.currentTimeMillis() - startTime) +" ms");
		};

		@Override
		protected void failed(Throwable e, Description description) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
	        Date date = new Date();
	        String filename = childClassName + "_"+dateFormat.format(date);
			captureScreenshot(filename);
		};
	};

	@SuppressWarnings({ "unused", "unchecked" })
	private Map<String, Object> WebTimings(WebDriverExtended webDriver) {
		Map<String, Object> webTiming = (HashMap<String, Object>) ((JavascriptExecutor) webDriver)
				.executeScript("var performance = window.performance || {};"
						+ "var timings = performance.timing || {};" + "return timings;");
		/*
		 * The dictionary returned will contain something like the following.
		 * The values are in milliseconds since 1/1/1970
		 * 
		 * connectEnd: 1280867925716 connectStart: 1280867925687
		 * domainLookupEnd: 1280867925687 domainLookupStart: 1280867925687
		 * fetchStart: 1280867925685 legacyNavigationStart: 1280867926028
		 * loadEventEnd: 1280867926262 loadEventStart: 1280867926155
		 * navigationStart: 1280867925685 redirectEnd: 0 redirectStart: 0
		 * requestEnd: 1280867925716 requestStart: 1280867925716 responseEnd:
		 * 1280867925940 responseStart: 1280867925919 unloadEventEnd:
		 * 1280867925940
		 */
		return webTiming;
	}

	private void captureScreenshot(String fileName) {
		try {
			// Make sure that the directory is there
			new File(Screenshot.DEFAULT_SCREENSHOT_DIRECTORY).mkdirs();
			FileOutputStream out = new FileOutputStream(Screenshot.DEFAULT_SCREENSHOT_DIRECTORY + "screenshot-"
					+ fileName + ".png");
			out.write(webDriver.getScreenshot());
			out.close();
		} catch (Exception e) {
			LOG.error("Could not take the screenshot! Cause: " + e.getClass().getName());
            // e.printStackTrace();
		}
	}

	public WebDriverExtended getWebDriver() {
		return webDriver;
	}

	public final Properties getTestConfigProperties(String properties) {
		InputStream stream = null;
		try {
			Properties prop = new Properties();
			stream = SeleniumTestCaseJUnit.class.getResourceAsStream(properties);
			prop.load(stream);
			return prop;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}
