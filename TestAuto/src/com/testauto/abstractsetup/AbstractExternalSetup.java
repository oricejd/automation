package com.testauto.abstractsetup;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openqa.selenium.Platform.ANY;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.testauto.seleniumsetup.WebDriverExtended;

public abstract class AbstractExternalSetup {

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

			WebDriver baseWebDriver = getBrowserDriver(grid);
			
			webDriver = new WebDriverExtended(baseWebDriver, baseUrl);
			webDriver.manage().timeouts().implicitlyWait(10, SECONDS);
			webDriver.manage().timeouts().pageLoadTimeout(10, SECONDS);
			
//			FirefoxProfile ffProfile = new FirefoxProfile();
//			try {
//				DesiredCapabilities dc = DesiredCapabilities.firefox();
//				dc.setCapability("platform", ANY);
//
//				if ("yes".equalsIgnoreCase(grid)) {
//					dc.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
//					baseWebDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
//				} else {
//					baseWebDriver = new FirefoxDriver(ffProfile);
//				}
//
//				baseWebDriver.manage().window().maximize();
//				((JavascriptExecutor) baseWebDriver).executeScript("window.focus();");
//				baseUrl = System.getProperty("baseUrl");
//
//				webDriver = new WebDriverExtended(baseWebDriver, baseUrl);
//				webDriver.manage().timeouts().implicitlyWait(10, SECONDS);
//				webDriver.manage().timeouts().pageLoadTimeout(10, SECONDS);
//			} catch (MalformedURLException e) {
//				e.printStackTrace();
//			}
		};

		@Override
		protected void finished(Description description) {
//			try {
//				Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
//			} catch (IOException e) {
//			}
//			try {
//				Runtime.getRuntime().exec("taskkill /F /IM plugin-container.exe");
//			} catch (IOException e) {
//			}
//			try {
//				Runtime.getRuntime().exec("taskkill /F /IM WerFault.exe");
//			} catch (IOException e) {
//			}

			webDriver.quit();
			LOG_TIME.info(childClassName + ": " + (System.currentTimeMillis() - startTime));
		};

		@Override
		protected void failed(Throwable e, Description description) {
			captureScreenshot(childClassName + "_" + System.currentTimeMillis());
		};
	};

	@SuppressWarnings({ "unused", "unchecked" })
	private Map<String, Object> WebTimings(WebDriverExtended webDriver) {
		Map<String, Object> webTiming = (HashMap<String, Object>) ((JavascriptExecutor) webDriver)
				.executeScript("var performance = window.performance || {};"
						+ "var timings = performance.timing || {};" + "return timings;");
		return webTiming;
	}

	private void captureScreenshot(String fileName) {
		try {
			// Make sure that the directory is there
			new File("target/surefire-reports/").mkdirs();
			FileOutputStream out = new FileOutputStream("target/surefire-reports/screenshot-"
					+ fileName + ".png");
			out.write(webDriver.getScreenshot());
			out.close();
		} catch (Exception e) {
			LOG.info("Could not take the screenshot");
			e.printStackTrace();
		}
	}

	public WebDriverExtended getWebDriver() {
		return webDriver;
	}

	public final Properties getTestConfigProperties(String properties) {
		InputStream stream = null;
		try {
			Properties prop = new Properties();
			stream = AbstractExternalSetup.class.getResourceAsStream(properties);
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
	
	//returns specific browser driver instance
	public WebDriver getBrowserDriver(String grid){
		WebDriver baseWebDriver = null;
		String browser = System.getProperty("browser");
		
		switch (browser) {
         case "firefox":
             //
     		FirefoxProfile ffProfile = new FirefoxProfile();

			try {
				DesiredCapabilities dc = DesiredCapabilities.firefox();
				dc.setCapability("platform", ANY);

				if ("yes".equalsIgnoreCase(grid)) {
					dc.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
					baseWebDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
				} else {
					baseWebDriver = new FirefoxDriver(ffProfile);
				}

				baseWebDriver.manage().window().maximize();
				((JavascriptExecutor) baseWebDriver).executeScript("window.focus();");
				baseUrl = System.getProperty("baseUrl");


			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
        	 // end Firefox
             break;
         case "chrome":
             //
        	 ChromeOptions options = new ChromeOptions();
        	 //options.addExtensions(new File("/path/to/extension.crx"));
        	 options.setBinary(new File("/drivers/chromedriver.exe"));
  			DesiredCapabilities dc = DesiredCapabilities.chrome();
  			dc.setCapability(ChromeOptions.CAPABILITY, options);
        	 
     		try {
				dc.setCapability("platform", ANY);

				if ("yes".equalsIgnoreCase(grid)) {
					dc.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
					baseWebDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
				} else {
					baseWebDriver = new ChromeDriver(dc);
				}

				baseWebDriver.manage().window().maximize();
				((JavascriptExecutor) baseWebDriver).executeScript("window.focus();");
				baseUrl = System.getProperty("baseUrl");

			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
        	
        	 //End Chrome
             break;
         case "ie":
             //ToDo
             break;
         case "opera":
             //ToDo
             break;
//         default:
//             throw new IllegalArgumentException("Invalid day of the week: " + dayOfWeekArg);
     }
		
		//
		return baseWebDriver;
	}
}
