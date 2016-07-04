/**
 * 
 */
package com.testauto.setuptestng;

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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.testauto.seleniumsetup.WebDriverExtended;
import com.testauto.setuptestng.Screenshot;
import com.testauto.util.angular.NgWebDriver;

/**
 * @author apopa
 *
 */
public class SeleniumTestCaseBase {
	
	public static Logger LOG = Logger.getLogger("testLogger");
	public static Logger LOG_TIME = Logger.getLogger("timeLogger");

//	private long startTime;
	private String baseUrl;
	private String childClassName = this.getClass().getName();
	private String browser = "firefox";
	private String grid= "no";
	
    public static NgWebDriver ngWebDriver;
    public static WebDriverExtended webDriver;
    public  static WebDriver driver;
	
	@BeforeClass
	public void beforeClass(){
		

	}
	
	@AfterClass
	public void afterClass(){	

	}
	
	@BeforeSuite
	public void beforeSuite() throws Exception{	
		
		browser = "firefox";//System.getProperty("browserName");
		grid = "no";//System.getProperty("grid");
		
		driver = getBrowserDriver(browser, grid);
		//
		baseUrl = System.getProperty("baseUrl");
		
		webDriver = new WebDriverExtended(driver);
//		webDriver = new WebDriverExtended(webDriver, baseUrl);
		ngWebDriver = new NgWebDriver((JavascriptExecutor)driver);
		
		webDriver.manage().timeouts().implicitlyWait(10, SECONDS);
		webDriver.manage().timeouts().pageLoadTimeout(10, SECONDS);
		
		webDriver.manage().window().maximize();
		webDriver.executeScript("window.focus();");

	}
	
	@AfterSuite
	public void afrerSuite() throws Exception{
		
		webDriver.quit();
		driver.quit();
		
	}
	
	@BeforeTest
	public void beforeTest() throws Exception{	
	}
	
	@AfterTest
	public void afrerTest() throws Exception{
		
	}
	
	@BeforeMethod
	public void beforeMethod(){
		

	}
	
	@AfterMethod
	public void afterMethod(){	

	}


//	public WebDriverExtended getWebDriver() {
//		return webDriver;
//	}
//	
//	public NgWebDriver getNgWebDriver() {
//		return ngWebDriver;
//	}
	
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

	public final Properties getTestConfigProperties(String properties) {
		InputStream stream = null;
		try {
			Properties prop = new Properties();
			stream = SeleniumTestCaseBase.class.getResourceAsStream(properties);
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
	public static WebDriver getBrowserDriver(String browser, String grid){
		WebDriver baseWebDriver = null;
		String os = System.getProperty("os.name").toLowerCase();
		
		switch (browser) {
		// Firefox Web Driver init here
         case "firefox":
             //
     		FirefoxProfile ffProfile = new FirefoxProfile();

			try {
				DesiredCapabilities dc = DesiredCapabilities.firefox();
				dc.setCapability("platform", ANY);

				if ("yes".equalsIgnoreCase(grid)) {
					dc.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
					baseWebDriver = new EventFiringWebDriver(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc)).register(new MyEventListener());
//					baseWebDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
				} else {
					baseWebDriver = new EventFiringWebDriver(new FirefoxDriver(ffProfile)).register(new MyEventListener());
//					baseWebDriver = new FirefoxDriver(ffProfile);
				}

			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
        	 // end Firefox
             break;
         // Chrome Web Driver init here    
         case "chrome":
             //
     		try {
           	 ChromeOptions options = new ChromeOptions();
           	 //options.addExtensions(new File("/path/to/extension.crx"));
           	 options.setBinary(new File("/drivers/chromedriver.exe"));
     			DesiredCapabilities dc = DesiredCapabilities.chrome();
     			dc.setCapability(ChromeOptions.CAPABILITY, options);
     			
				dc.setCapability("platform", ANY);

				if ("yes".equalsIgnoreCase(grid)) {
					dc.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
					baseWebDriver = new EventFiringWebDriver(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc)).register(new MyEventListener());
//					baseWebDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
				} else {
					
					System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
//					baseWebDriver = new EventFiringWebDriver(new ChromeDriver(dc)).register(new MyEventListener());
					baseWebDriver = new EventFiringWebDriver(new ChromeDriver()).register(new MyEventListener());
					
//					baseWebDriver = new ChromeDriver(dc);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
        	 //End Chrome
             break;
         case "ie":
 			try {
 				DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
 				dc.setCapability("platform", ANY);

 				if ("yes".equalsIgnoreCase(grid)) {
 					dc.setBrowserName(DesiredCapabilities.internetExplorer().getBrowserName());
 					baseWebDriver = new EventFiringWebDriver(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc)).register(new MyEventListener());
// 					baseWebDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
 				} else {
 					System.setProperty("webdriver.ie.driver", "drivers/IEDriverServer.exe");
 					driver = new EventFiringWebDriver(new InternetExplorerDriver()).register(new MyEventListener());
 				}

 			} catch (MalformedURLException e) {
 				e.printStackTrace();
 			}

             break;
         case "opera":
     		try {
				if ("yes".equalsIgnoreCase(grid)) {
			      	 OperaOptions optionsOpera = new OperaOptions();
		        	 optionsOpera.setBinary(new File("drivers/operadriver.exe"));
		  			@SuppressWarnings("deprecation") 
		  			DesiredCapabilities dcOpera = DesiredCapabilities.opera();
		  			dcOpera.setCapability(OperaOptions.CAPABILITY, optionsOpera);
					dcOpera.setCapability("platform", ANY);
					dcOpera.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
					baseWebDriver = new EventFiringWebDriver(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dcOpera)).register(new MyEventListener());
//					baseWebDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
				} else {
			    	System.setProperty("webdriver.opera.driver", "drivers/operadriver.exe");
			    	baseWebDriver = new EventFiringWebDriver(new OperaDriver()).register(new MyEventListener());
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
             //End Opera driver
             break;
         default:
             System.out.println("Not able to set Driver object: Unknown Browser");
     }
		//
		return baseWebDriver;
	}
	
	// Capture screenshot 
		private void captureScreenshot(String fileName, WebDriverExtended webDriver) {
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
}
