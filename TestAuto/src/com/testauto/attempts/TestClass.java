/**
 * 
 */
package com.testauto.attempts;

import static org.openqa.selenium.By.xpath;
import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.testauto.seleniumsetup.WebDriverExtended;
import com.testauto.setuptestng.MyEventListener;
import com.testauto.util.angular.ByAngular;
import com.testauto.util.angular.NgWebDriver;
import org.seleniumhq.selenium.fluent.*;

/**
 * @author apopa
 *
 */
//@Listeners({com.testauto.setuptestng.listeners.Listener.class})
public class TestClass {

    private NgWebDriver ngWebDriver;
    private WebDriver driver;
    private WebDriverExtended extDriver;
    
    @BeforeSuite
    public void before_suite() throws Exception {

        // Launch Protractor's own test app on http://localhost:8080
//        ((StdErrLog) Log.getRootLogger()).setLevel(StdErrLog.LEVEL_OFF);
//        webServer = new Server(new QueuedThreadPool(5));
//        ServerConnector connector = new ServerConnector(webServer, new HttpConnectionFactory());
//        connector.setPort(8080);
//        webServer.addConnector(connector);
//        ResourceHandler resource_handler = new ResourceHandler();
//        resource_handler.setDirectoriesListed(true);
//        resource_handler.setWelcomeFiles(new String[]{"index.html"});
//        resource_handler.setResourceBase("resources/webapp");
//        HandlerList handlers = new HandlerList();
//        MovedContextHandler effective_symlink = new MovedContextHandler(webServer, "/lib/angular", "/lib/angular_v1.2.9");
//        handlers.setHandlers(new Handler[] { effective_symlink, resource_handler, new DefaultHandler() });
//        webServer.setHandler(handlers);
//        webServer.start();

    	System.setProperty("webdriver.opera.driver", "drivers/operadriver.exe");
//        drSiver = new OperaDriver();
        
        driver = new EventFiringWebDriver(new OperaDriver()).register(new MyEventListener());
        
        //
//        ChromeOptions options = new ChromeOptions();
//   	 	//options.addExtensions(new File("/path/to/extension.crx"));
//        options.setBinary(new File("drivers/chromedriver.exe"));
//        DesiredCapabilities dc = DesiredCapabilities.chrome();
//        dc.setCapability(ChromeOptions.CAPABILITY, options);
//        String baseUrl;
//        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
//        
//        dc.setCapability("platform", ANY);
//		driver = new ChromeDriver(dc);
//		driver.manage().window().maximize();
//		((JavascriptExecutor) driver).executeScript("window.focus();");
//		String baseUrl = System.getProperty("baseUrl");
//		
//		System.out.println("#########################");
//		System.out.println("Base url is: " + baseUrl);
//		System.out.println("#########################");
//        
//        //
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		ngWebDriver = new NgWebDriver((JavascriptExecutor)driver);
   //     ngWebDriver = new NgWebDriver(driver);
    }
    
    @AfterSuite
    public void after_suite() throws Exception {
    	driver.quit();
//        driver.quit();
    }

    @BeforeMethod
    public void resetBrowser() {
        driver.get("about:blank");
    }

    @Test
    public void testAttempt() {

        driver.get("http://www.angularjshub.com/code/examples/basics/02_TwoWayDataBinding_HTML/index.demo.php");
//        driver.get("http://https://weather.com/en-GB");
//        ngWebDriver.waitForAngularRequestsToFinish();

        WebElement firstname = driver.findElement(ByAngular.model("firstName"));
//        WebElement firstname = driver.findElement(By.id("username"));
        firstname.clear();
        firstname.sendKeys("Mary");
        assertEquals(driver.findElement(xpath("//input")).getAttribute("value"), "Mary");

    }
    
}
