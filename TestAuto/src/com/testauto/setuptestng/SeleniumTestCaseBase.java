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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

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
    public static WebDriver driver;
//    private WebDriver driver;

	
	@BeforeClass
	public void beforeClass(){
		
	}
	
	
	@AfterClass
	public void afterClass(){
		
	}
	
	@BeforeSuite
	public void beforeSuite() throws Exception{
//		WebDriver driver = getBrowserDriver(grid);
		
		//
		//
		
    	System.setProperty("webdriver.opera.driver", "drivers/operadriver.exe");
    	driver = new EventFiringWebDriver(new OperaDriver()).register(new MyEventListener());
      
		
		//
		//
		baseUrl = System.getProperty("baseUrl");
		
//		webDriver = new WebDriverExtended(driver);
		webDriver = new WebDriverExtended(webDriver, baseUrl);
		ngWebDriver = new NgWebDriver((JavascriptExecutor)driver);
		
		webDriver.manage().window().maximize();
		webDriver.executeScript("window.focus();");
		
		webDriver.manage().timeouts().implicitlyWait(10, SECONDS);
		webDriver.manage().timeouts().pageLoadTimeout(10, SECONDS);
	}
	
	@AfterSuite
	public void afrerSuite() throws Exception{
		webDriver.quit();
		driver.quit();
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
        	 ChromeOptions options = new ChromeOptions();
        	 //options.addExtensions(new File("/path/to/extension.crx"));
        	 options.setBinary(new File("/drivers/chromedriver.exe"));
  			DesiredCapabilities dc = DesiredCapabilities.chrome();
  			dc.setCapability(ChromeOptions.CAPABILITY, options);
        	 
     		try {
				dc.setCapability("platform", ANY);

				if ("yes".equalsIgnoreCase(grid)) {
					dc.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
					baseWebDriver = new EventFiringWebDriver(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc)).register(new MyEventListener());
//					baseWebDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
				} else {
					baseWebDriver = new EventFiringWebDriver(new ChromeDriver(dc)).register(new MyEventListener());
//					baseWebDriver = new ChromeDriver(dc);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
        	
        	 //End Chrome
             break;
         case "ie":
             //ToDo
             break;
         case "opera":
         	 ChromeOptions optionsOpera = new ChromeOptions();
        	 //options.addExtensions(new File("/path/to/extension.crx"));
        	 optionsOpera.setBinary(new File("/drivers/operadriver.exe"));
  			DesiredCapabilities dcOpera = DesiredCapabilities.chrome();
  			dcOpera.setCapability(ChromeOptions.CAPABILITY, optionsOpera);
        	 
     		try {
     			dcOpera.setCapability("platform", ANY);

				if ("yes".equalsIgnoreCase(grid)) {
					dcOpera.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
					baseWebDriver = new EventFiringWebDriver(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dcOpera)).register(new MyEventListener());
//					baseWebDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
				} else {
					baseWebDriver = new EventFiringWebDriver(new ChromeDriver(dcOpera)).register(new MyEventListener());
//					baseWebDriver = new ChromeDriver(dc);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
        	 
             //End Opera driver
             break;
//         default:
//             Do nothing for now
//             ToDo
     }
		
		//
		return baseWebDriver;
	}
	
	// Capture screenshot 
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
}
