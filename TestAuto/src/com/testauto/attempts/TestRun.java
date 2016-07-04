package com.testauto.attempts;

import static org.openqa.selenium.By.xpath;
import static org.testng.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.testauto.abstractsetup.AbstractExternalSetupTest;
import com.testauto.seleniumsetup.WebDriverExtended;
import com.testauto.setuptestng.SeleniumTestCaseBase;
//import com.testauto.setuptestng.SeleniumTestCaseBase;
import com.testauto.util.angular.ByAngular;
import com.testauto.util.angular.NgWebDriver;

@Listeners(com.testauto.setuptestng.listeners.TestListener.class)
public class TestRun  extends SeleniumTestCaseBase{

	   @Test
	    public void testRunAttempt() {

		    webDriver.gotoUrl("http://www.angularjshub.com/code/examples/basics/02_TwoWayDataBinding_HTML/index.demo.php");
	        ngWebDriver.waitForAngularRequestsToFinish();
	        WebElement firstname = webDriver.findElement(ByAngular.model("firstName"));
	        
	        firstname.clear();
	        try {
				Thread.sleep(2000);
			
	        firstname.sendKeys("Mary");
	        Thread.sleep(2000);
	        assertEquals(webDriver.findElement(xpath("//input")).getAttribute("value"), "Mary2");
	        } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	   
	   @Test
	    public void testRunAttempt2() {

		    webDriver.gotoUrl("http://www.angularjshub.com/code/examples/basics/02_TwoWayDataBinding_HTML/index.demo.php");
	        ngWebDriver.waitForAngularRequestsToFinish();
	        WebElement firstname = webDriver.findElement(ByAngular.model("firstName"));
	        
	        firstname.clear();
	        try {
				Thread.sleep(2000);
			
	        firstname.sendKeys("Mary");
	        Thread.sleep(2000);
	        assertEquals(webDriver.findElement(xpath("//input")).getAttribute("value"), "Mary");
	        } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

}
